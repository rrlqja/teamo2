package song.teamo2.domain.teaming.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import song.teamo2.domain.team.repository.TeamJpaRepository;
import song.teamo2.domain.team.service.TeamMemberService;
import song.teamo2.domain.teaming.repository.TeamingJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamingService {
    private final TeamMemberService teamMemberService;
    private TeamingJpaRepository teamingRepository;
    private TeamJpaRepository teamRepository;
}
