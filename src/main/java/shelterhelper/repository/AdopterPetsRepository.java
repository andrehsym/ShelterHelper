package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelterhelper.model.AdoptedPets;

@Repository("adopterPetsRepository")
public interface AdopterPetsRepository extends JpaRepository<AdoptedPets, Long> {
}
