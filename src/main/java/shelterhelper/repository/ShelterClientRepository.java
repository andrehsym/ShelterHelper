package shelterhelper.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.ShelterClient;


/**
 * Репозиторий. Просто репозиторий
 * написать в репозитории метод
 */
@Repository
public interface ShelterClientRepository extends JpaRepository<ShelterClient, Long>{

}
