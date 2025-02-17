package song.teamo2.domain.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.application.entity.Application;
import song.teamo2.domain.application.entity.ApplicationStatus;
import song.teamo2.domain.application.repository.ApplicationJpaRepository;
import song.teamo2.domain.common.exception.application.exceptions.AlreadyExistApplicationException;
import song.teamo2.domain.common.exception.application.exceptions.ApplicationApprovalAccessDeniedException;
import song.teamo2.domain.common.exception.application.exceptions.ApplicationNotFoundException;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.entity.TeamRole;
import song.teamo2.domain.team.service.TeamMemberService;
import song.teamo2.domain.teaming.dto.ApplicationForm;
import song.teamo2.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationJpaRepository applicationRepository;
    private final TeamMemberService teamMemberService;

    @Transactional
    public void validateExistApplication(User user, Team team) {
        applicationRepository.findApplicationByUserAndTeamAndStatus(user, team, ApplicationStatus.PENDING)
                .ifPresent(application -> {
                    throw new AlreadyExistApplicationException("이미 신청하였습니다.");
                });
    }

    @Transactional
    public Long createApplication(User user, Team team, ApplicationForm applicationForm) {
        Application application = Application.create(user, team, applicationForm.getTitle(), applicationForm.getContent());

        return applicationRepository.save(application).getId();
    }

    @Transactional
    public Long approveApplication(User user, Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        TeamMember teamMember = teamMemberService.isTeamMember(user, application.getTeam());
        if (teamMember == null || !teamMember.getRole().equals(TeamRole.ADMIN)) {
            throw new ApplicationApprovalAccessDeniedException("승인할 수 없습니다.");
        }

        teamMemberService.saveTeamMember(application.getUser(), application.getTeam(), TeamRole.USER);

        application.approve();
        applicationRepository.save(application);

        return application.getTeam().getId();
    }
}
