package typeqast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import typeqast.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
