package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shelterhelper.model.ShelterPetsPhotos;

import java.util.List;

public interface ShelterPetsPhotosRepository extends JpaRepository<ShelterPetsPhotos, Long> {

    List<ShelterPetsPhotos> findAllByShelterPets_IdPet(Long idPet);

}
