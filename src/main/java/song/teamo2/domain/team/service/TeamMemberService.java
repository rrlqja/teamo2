package song.teamo2.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo2.domain.common.exception.teamMember.exceptions.DuplicatedTeamMemberException;
import song.teamo2.domain.common.exception.teamMember.exceptions.TeamMemberNotFoundException;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.entity.TeamRole;
import song.teamo2.domain.team.repository.TeamJpaRepository;
import song.teamo2.domain.team.repository.TeamMemberJpaRepository;
import song.teamo2.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberJpaRepository teamMemberRepository;
    private final TeamJpaRepository teamRepository;

    @Transactional
    public Long saveTeamMember(User user, Team team, TeamRole teamRole) {
        teamMemberRepository.findTeamMemberByUserAndTeam(user, team)
                .ifPresent(teamMember -> {
                    throw new DuplicatedTeamMemberException("이미 가입된 사용자 입니다.");
                });

        TeamMember teamMember = TeamMember.of(user, team, teamRole);

        return teamMemberRepository.save(teamMember).getId();
    }

    @Transactional
    public Page<TeamMember> getTeamMemberPage(User user, Pageable pageable) {
        return teamMemberRepository.findTeamMembersByUser(user, pageable);
    }

    @Transactional
    public TeamMember isTeamMember(User user, Team team) {
        return teamMemberRepository.findTeamMemberByUserAndTeam(user, team)
                .orElse(null);
    }

    @Transactional
    public void exitTeam(User user, Team team) {
        TeamMember teamMember = teamMemberRepository.findTeamMemberByUserAndTeam(user, team)
                .orElseThrow(() -> new TeamMemberNotFoundException("잘못된 요청입니다."));

        if (teamMember.getRole() == TeamRole.ADMIN) {
            teamMemberRepository.deleteTeamMembersByTeam(teamMember.getTeam());
            teamRepository.delete(team);
        }

        teamMemberRepository.delete(teamMember);
    }
}
