package song.teamo2.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.application.dto.ApplicationPageDto;
import song.teamo2.domain.application.entity.Application;
import song.teamo2.domain.application.entity.ApplicationStatus;
import song.teamo2.domain.application.repository.ApplicationJpaRepository;
import song.teamo2.domain.common.exception.application.exceptions.ApplicationAccessDeniedException;
import song.teamo2.domain.common.exception.team.exceptions.TeamModificationAccessDeniedException;
import song.teamo2.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo2.domain.common.exception.team.exceptions.TeamingCreationAccessDeniedException;
import song.teamo2.domain.common.exception.teamMember.exceptions.RemoveTeamMemberAccessDeniedException;
import song.teamo2.domain.common.exception.teamMember.exceptions.TeamMemberAccessDeniedException;
import song.teamo2.domain.team.dto.CreateTeamDto;
import song.teamo2.domain.team.dto.CreateTeamingDto;
import song.teamo2.domain.team.dto.ModifyTeamDto;
import song.teamo2.domain.team.dto.TeamDto;
import song.teamo2.domain.team.dto.TeamMemberPageDto;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.entity.TeamRole;
import song.teamo2.domain.team.repository.TeamJpaRepository;
import song.teamo2.domain.teaming.entity.Teaming;
import song.teamo2.domain.teaming.repository.TeamingJpaRepository;
import song.teamo2.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamMemberService teamMemberService;
    private final TeamJpaRepository teamRepository;
    private final TeamingJpaRepository teamingRepository;
    private final ApplicationJpaRepository applicationRepository;

    @Transactional
    public Long createTeam(User user, CreateTeamDto createTeamDto) {
        Team team = createTeamDto.toEntity();

        Team saveTeam = teamRepository.save(team);

        teamMemberService.saveTeamMember(user, team, TeamRole.ADMIN);

        return saveTeam.getId();
    }

    @Transactional
    public Page<Team> getUserTeamPage(User user, Pageable pageable) {
        Page<TeamMember> teamMemberPage = teamMemberService.getTeamMemberPageByUser(user, pageable);
        List<Long> teamIdList = teamMemberPage.map(teamMember -> teamMember.getTeam().getId()).toList();

        return teamRepository.findTeamsByIdIn(teamIdList, pageable);
    }

    @Transactional
    public TeamDto getTeam(User user, Long teamId) {
        Team team = findTeamById(teamId);

        if (user == null) {
            return new TeamDto(team);
        }

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null) {
            return new TeamDto(team);
        }

        if (teamMember.getRole() == TeamRole.ADMIN) {
            return new TeamDto(team, true, true);
        } else{
            return new TeamDto(team, false, true);
        }
    }

    @Transactional
    public void exitTeam(User user, Long teamId) {
        Team team = findTeamById(teamId);

        teamMemberService.exitTeam(user, team);
    }

    @Transactional
    public ModifyTeamDto getTeamForModify(User user, Long teamId) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);

        if (teamMember != null && teamMember.getRole() == TeamRole.ADMIN) {
            return new ModifyTeamDto(team.getTeamName(), team.getTeamInfo());
        }

        return null;
    }

    @Transactional
    public Long modifyTeam(User user, Long teamId, ModifyTeamDto modifyTeamDto) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || teamMember.getRole() != TeamRole.ADMIN) {
            throw new TeamModificationAccessDeniedException("수정할 수 없습니다.");
        }

        team.modify(modifyTeamDto.getTeamName(), modifyTeamDto.getTeamInfo());

        return teamRepository.save(team).getId();
    }

    @Transactional
    public void getCreateTeaming(User user, Long teamId) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || teamMember.getRole() != TeamRole.ADMIN) {
            throw new TeamingCreationAccessDeniedException("티밍을 생성할 수 없습니다.");
        }
    }

    @Transactional
    public Long createTeaming(User user, Long teamId, CreateTeamingDto createTeamingDto) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || teamMember.getRole() != TeamRole.ADMIN) {
            throw new TeamingCreationAccessDeniedException("티밍을 생성할 수 없습니다.");
        }

        Teaming teaming = Teaming.create(user, team, createTeamingDto.getTitle(), createTeamingDto.getContent());
        return teamingRepository.save(teaming).getId();
    }

    @Transactional
    public Page<ApplicationPageDto> getApplicationPage(User user, Long teamId, Pageable pageable) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || !teamMember.getRole().equals(TeamRole.ADMIN)) {
            throw new ApplicationAccessDeniedException("잘못된 요청입니다.");
        }

        Page<Application> applicationPage = applicationRepository.findApplicationsByTeam(team, ApplicationStatus.PENDING, pageable);

        return applicationPage.map(ApplicationPageDto::new);
    }

    @Transactional
    public Page<TeamMemberPageDto> getTeamMemberPage(User user, Long teamId, Pageable pageable) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || teamMember.getRole() != TeamRole.ADMIN) {
            throw new TeamMemberAccessDeniedException("잘못된 요청입니다.");
        }

        return teamMemberService.getTeamMemberPageByTeam(team, pageable)
                .map(TeamMemberPageDto::new);
    }

    @Transactional
    public Long removeTeamMember(User user, Long teamId, Long teamMemberUserId) {
        Team team = findTeamById(teamId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, team);
        if (teamMember == null || teamMember.getRole() != TeamRole.ADMIN) {
            throw new TeamMemberAccessDeniedException("권한이 없습니다.");
        }

        if (user.getId().equals(teamMember.getUser().getId())) {
            throw new RemoveTeamMemberAccessDeniedException("잘못된 요청입니다.");
        }

        teamMemberService.removeTeamMemberByTeamAndUserId(team, teamMemberUserId);

        return team.getId();
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }
}
