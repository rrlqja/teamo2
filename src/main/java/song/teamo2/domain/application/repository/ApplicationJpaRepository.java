package song.teamo2.domain.application.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.application.entity.Application;
import song.teamo2.domain.application.entity.ApplicationStatus;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {
    @Query("select a " +
            " from Application a " +
            "where a.user = :user " +
            "  and a.team = :team " +
            "  and a.status = :status ")
    Optional<Application> findApplicationByUserAndTeamAndStatus(@Param("user") User user,
                                                                @Param("team") Team team,
                                                                @Param("status")ApplicationStatus status);

    @Query("select a " +
            " from Application a " +
            "where a.team = :team " +
            "  and a.status = :status")
    Page<Application> findApplicationsByTeam(@Param("team") Team team,
                                             @Param("status") ApplicationStatus status,
                                             Pageable pageable);

    @Query("select a " +
            " from Application a " +
            " join fetch a.team " +
            " join fetch a.user " +
            "where a.id = :id ")
    Optional<Application> findById(@Param("id") Long id);
}
