package song.teamo2.domain.teaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import song.teamo2.domain.teaming.entity.Teaming;

@Repository
public interface TeamingJpaRepository extends JpaRepository<Teaming, Long> {

}
