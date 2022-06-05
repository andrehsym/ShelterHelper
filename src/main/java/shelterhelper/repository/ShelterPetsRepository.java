package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.ShelterPets;

import java.util.Collection;

@Repository
public interface ShelterPetsRepository extends JpaRepository<ShelterPets, Long> {
    /**
     * Список питомцев из БД, которые уже усыновлены
     * is_used = TRUE
     */
    @Query(value = "SELECT  *  FROM shelter_pets WHERE is_used = TRUE ", nativeQuery = true)
    Collection<ShelterPets> getAdoptedPets();

    /**
     * Список питомцев из БД, которые на испытательном сроке
     * критерии - нвходятсяв текущей БД
     * нвходятся в БД adopted_dog + is_checking = true
     */
    @Query(value = "SELECT  *  FROM shelter_pets " +
            "JOIN adopter_dogs ON adopter_pets.id_pet = shelter_pets.id_pet",
            nativeQuery = true)
    Collection<ShelterPets> getCheckingDogs();

    @Query(value = "SELECT  *  FROM shelter_pets " +
            "JOIN adopter_cats ON adopter_pets.id_pet = shelter_pets.id_pet",
            nativeQuery = true)
    Collection<ShelterPets> getCheckingCats();


}
