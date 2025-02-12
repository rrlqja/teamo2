package song.teamo2.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.team.entity.TeamRole;
import song.teamo2.domain.team.repository.TeamJpaRepository;
import song.teamo2.domain.team.repository.TeamMemberJpaRepository;
import song.teamo2.domain.user.entity.User;
import song.teamo2.domain.user.repository.UserJpaRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final TeamJpaRepository teamRepository;
        private final TeamMemberJpaRepository teamMemberRepository;

        public void init() {
            User user1 = userRepository.save(createUser("1", "1"));
            Team team1 = teamRepository.save(createTeam("t1", "t info1"));
            TeamMember teamMEmber1 = teamMemberRepository.save(createTeamMember(user1, team1, TeamRole.ADMIN));
        }

        private static TeamMember createTeamMember(User user, Team team, TeamRole teamRole) {
            return TeamMember.of(user, team, teamRole);
        }

        private static Team createTeam(String teamName, String teamInfo) {
            return Team.of(teamName, teamInfo);
        }

        private User createUser(String username, String password) {
            return User.of(username, passwordEncoder.encode(password));
        }
    }
}
