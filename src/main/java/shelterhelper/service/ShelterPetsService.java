package shelterhelper.service;

import shelterhelper.model.ShelterPets;

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
    ShelterPets addPet(ShelterPets pet);
    ShelterPets getPet(Long id);

    Collection<ShelterPets> getAllPets();
    Collection<ShelterPets> getAdoptedPets();
    Collection<ShelterPets> getCheckingDogs();
    Collection<ShelterPets> getCheckingCats();

    ShelterPets setPet(Long id);

    boolean removePet(Long id);
}
