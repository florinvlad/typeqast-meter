package typeqast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import typeqast.entities.Reading;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Integer> {
}
