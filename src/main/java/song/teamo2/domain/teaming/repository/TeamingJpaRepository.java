package song.teamo2.domain.teaming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.teaming.entity.Teaming;

import java.util.Optional;

@Repository
public interface TeamingJpaRepository extends JpaRepository<Teaming, Long> {
    @Query("select t " +
            " from Teaming t " +
            " join fetch t.writer " +
            " join fetch t.team " +
            "where t.id = :id")
    Optional<Teaming> findById(@Param("id") Long id);

    @Query("select t " +
            " from Teaming t " +
            " join fetch t.writer ")
    Page<Teaming> findAll(Pageable pageable);
}
