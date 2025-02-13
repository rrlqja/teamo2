package song.teamo2.domain.teaming.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.teaming.entity.Teaming;

@Repository
public interface TeamingJpaRepository extends JpaRepository<Teaming, Long> {

    @Query("select t " +
            " from Teaming t " +
            " join fetch t.writer ")
    Page<Teaming> findAll(Pageable pageable);
}
