package song.teamo2.domain.team.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.team.entity.Team;

import java.util.Collection;

@Repository
public interface TeamJpaRepository extends JpaRepository<Team, Long> {
    @Query("select t " +
            " from Team t " +
            "where t.id in :ids")
    Page<Team> findTeamsByIdIn(@Param("ids") Collection<Long> ids,
                               Pageable pageable);
}
