package song.teamo2.domain.team.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.team.entity.Team;
import song.teamo2.domain.team.entity.TeamMember;
import song.teamo2.domain.user.entity.User;

import java.util.Optional;

@Repository
public interface TeamMemberJpaRepository extends JpaRepository<TeamMember, Long> {
    @Query("select tm " +
            " from TeamMember tm " +
            "where tm.user = :user " +
            "  and tm.team = :team ")
    Optional<TeamMember> findTeamMemberByUserAndTeam(@Param("user") User user,
                                                     @Param("team") Team team);

    @Query("select tm " +
            " from TeamMember tm " +
            " join fetch tm.user " +
            " join fetch tm.team " +
            "where tm.user = :user ")
    Page<TeamMember> findTeamMembersByUser(@Param("user") User user,
                                           Pageable pageable);

    @Query("select tm " +
            " from TeamMember tm " +
            " join fetch tm.user " +
            " join fetch tm.team " +
            "where tm.team = :team ")
    Page<TeamMember> findTeamMemberByTeam(@Param("team") Team team,
                                          Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete " +
            " from TeamMember tm " +
            "where tm.team = :team")
    void deleteTeamMembersByTeam(@Param("team") Team team);

    @Query("select tm " +
            " from TeamMember tm " +
            " join fetch tm.team " +
            " join fetch tm.user " +
            "where tm.team = :team " +
            "  and tm.user.id = :userId ")
    Optional<TeamMember> findTeamMemberByTeamAndUser_Id(@Param("team") Team team,
                                                        @Param("userId") Long userId);
}
