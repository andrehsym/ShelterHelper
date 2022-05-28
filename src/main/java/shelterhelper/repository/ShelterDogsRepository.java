package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.ShelterDogs;

import java.util.Collection;

@Repository
public interface ShelterDogsRepository extends JpaRepository <ShelterDogs, Long>{
    /**
     *  Список питомцев из БД, которые уже усыновлены
     *  is_used = TRUE
     */
    @Query(value = "SELECT  *  FROM shelter_dogs WHERE is_used = TRUE ", nativeQuery = true)
    Collection<ShelterDogs> getAdoptedPets();
    /**
     *  Список питомцев из БД, которые на испытательном сроке
     *  критерии - нвходятсяв текущей БД
     *  нвходятся в БД adopted_dog + is_checking = true
     */
    @Query(value = "SELECT  *  FROM shelter_dogs " +
                   "JOIN adopter_dog ON adopter_dog.id_dog = shelter_dogs.id_dog",
                     nativeQuery = true)
    Collection<ShelterDogs> getCheckingPets();
}
