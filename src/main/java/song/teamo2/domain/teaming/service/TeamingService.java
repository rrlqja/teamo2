package song.teamo2.domain.teaming.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.application.service.ApplicationService;
import song.teamo2.domain.common.exception.teamMember.exceptions.AlreadyExistTeamMemberException;
import song.teamo2.domain.common.exception.teaming.exceptions.TeamingModificationAccessDeniedException;
import song.teamo2.domain.common.exception.teaming.exceptions.TeamingNotFoundException;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.service.TeamMemberService;
import song.teamo2.domain.teaming.dto.ApplicationForm;
import song.teamo2.domain.teaming.dto.ModifyTeamingDto;
import song.teamo2.domain.teaming.dto.TeamingDto;
import song.teamo2.domain.teaming.dto.TeamingPageDto;
import song.teamo2.domain.teaming.entity.Teaming;
import song.teamo2.domain.teaming.repository.TeamingJpaRepository;
import song.teamo2.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamingService {
    private final TeamMemberService teamMemberService;
    private final TeamingJpaRepository teamingRepository;
    private final ApplicationService applicationService;

    @Transactional
    public Page<TeamingPageDto> getTeamingList(Pageable pageable) {

        return teamingRepository.findAll(pageable).map(TeamingPageDto::new);
    }

    @Transactional
    public TeamingDto getTeaming(User user, Long teamingId) {
        Teaming teaming = findTeamingById(teamingId);

        if (user == null) {
            return new TeamingDto(teaming, false, false);
        }

        TeamMember teamMember = teamMemberService.isTeamMember(user, teaming.getTeam());
        if (teamMember != null) {
            return new TeamingDto(teaming, teaming.getWriter().getId() == user.getId(), true);
        }

        return new TeamingDto(teaming, teaming.getWriter().getId() == user.getId(), false);
    }

    @Transactional
    public ModifyTeamingDto getTeamingForModify(User user, Long teamingId) {
        Teaming teaming = findTeamingById(teamingId);

        isWriter(user, teaming);

        return new ModifyTeamingDto(teaming);
    }

    @Transactional
    public Long modifyTeaming(User user, Long teamingId, ModifyTeamingDto modifyTeamingDto) {
        Teaming teaming = findTeamingById(teamingId);

        isWriter(user, teaming);

        teaming.modify(modifyTeamingDto.getTitle(), modifyTeamingDto.getContent());

        return teamingRepository.save(teaming).getId();
    }

    @Transactional
    public Long closeTeaming(User user, Long teamingId) {
        Teaming teaming = findTeamingById(teamingId);

        isWriter(user, teaming);

        teaming.toggleStatus();

        return teamingRepository.save(teaming).getId();
    }

    @Transactional
    public void validateApplicationForm(User user, Long teamingId) {
        Teaming teaming = findTeamingById(teamingId);

        applicationService.validateExistApplication(user, teaming.getTeam());
    }

    @Transactional
    public Long createApplication(User user, Long teamingId, ApplicationForm applicationForm) {
        Teaming teaming = findTeamingById(teamingId);

        TeamMember teamMember = teamMemberService.isTeamMember(user, teaming.getTeam());
        if (teamMember != null) {
            throw new AlreadyExistTeamMemberException("이미 가입한 팀 입니다.");
        }

        applicationService.validateExistApplication(user, teaming.getTeam());

        return applicationService.createApplication(user, teaming.getTeam(), applicationForm);
    }

    private Teaming findTeamingById(Long teamingId) {
        return teamingRepository.findById(teamingId)
                .orElseThrow(TeamingNotFoundException::new);
    }

    private void isWriter(User user, Teaming teaming) {
        if (user == null || teaming.getWriter().getId() != user.getId()) {
            throw new TeamingModificationAccessDeniedException("권한이 없습니다.");
        }
    }
}
