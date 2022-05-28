package shelterhelper.service;

import shelterhelper.model.ShelterDogs;

import java.util.Collection;

/**
 * методы интерфейса
 * addPet - добавить питомца в приют
 * setPet - отредактировать данные
 * removePet - удалить запись из БД
 * getAllPets - полный список записей БД
 * getAdoptedPets  - список уже усыновленных питомцев
 * getCheckingPets - список тех, кто на испытательном сроке
 */

public interface ShelterPetsService {
    ShelterDogs addPet(ShelterDogs pet);
    ShelterDogs getPet(Long id);

    Collection<ShelterDogs> getAllPets();
    Collection<ShelterDogs> getAdoptedPets();
    Collection<ShelterDogs> getCheckingPets();

    ShelterDogs setPet(Long id);

    boolean removePet(Long id);
}
