package typeqast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import typeqast.entities.Meter;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {
}
