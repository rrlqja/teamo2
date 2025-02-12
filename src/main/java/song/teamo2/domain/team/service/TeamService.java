package song.teamo2.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.common.exception.team.exceptions.TeamModificationAccessDeniedException;
import song.teamo2.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo2.domain.team.dto.CreateTeamDto;
import song.teamo2.domain.team.dto.ModifyTeamDto;
import song.teamo2.domain.team.dto.TeamDto;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.entity.TeamRole;
import song.teamo2.domain.team.repository.TeamJpaRepository;
import song.teamo2.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamMemberService teamMemberService;
    private final TeamJpaRepository teamRepository;

    @Transactional
    public Long createTeam(User user, CreateTeamDto createTeamDto) {
        Team team = createTeamDto.toEntity();

        Team saveTeam = teamRepository.save(team);

        teamMemberService.saveTeamMember(user, team, TeamRole.ADMIN);

        return saveTeam.getId();
    }

    @Transactional
    public Page<Team> getUserTeamPage(User user, Pageable pageable) {
        Page<TeamMember> teamMemberPage = teamMemberService.getTeamMemberPage(user, pageable);
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

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }
}
