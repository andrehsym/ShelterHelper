package shelterhelper.service;

import shelterhelper.model.AdoptedPets;

import java.util.Collection;

/**
 * интерфейс для работы с таблицами усыновителей
 */

public interface ShelterAdopterPetsService {
    AdoptedPets addAdopter(AdoptedPets pet);
    Collection<AdoptedPets> getAllAdopters();
    Collection<AdoptedPets> getAllCatAdopters();
    Collection<AdoptedPets> getAllDogAdopters();

    AdoptedPets setAdopter(AdoptedPets pet);
    boolean removeAdopter(Long id);
}

