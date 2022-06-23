package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.AdoptedPets;
import shelterhelper.model.ShelterObject;
import shelterhelper.model.ShelterPets;
import shelterhelper.repository.AdopterPetsRepository;
import shelterhelper.repository.ShelterObjectRepository;
import shelterhelper.repository.ShelterPetsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("adopterPetsService")
public class ShelterAdopterPetsServiceImpl implements ShelterAdopterPetsService {
    private final Logger logger = LoggerFactory.getLogger(ShelterAdopterPetsServiceImpl.class);
    private final AdopterPetsRepository adopterPetsRepository;
    private final ShelterObjectRepository shelterObjectRepository;
    private final ShelterPetsRepository shelterPetsRepository;

    public ShelterAdopterPetsServiceImpl(AdopterPetsRepository adopterPetsRepository, ShelterObjectRepository shelterObjectRepository, ShelterPetsRepository shelterPetsRepository) {
        this.adopterPetsRepository = adopterPetsRepository;
        this.shelterObjectRepository = shelterObjectRepository;
        this.shelterPetsRepository = shelterPetsRepository;
    }

    /**
     * добавление записи
     * можем добавить только по существующему питомцу!
     * @param pet экземпляр
     * @return экземпляр класса
     */
    @Override
    public AdoptedPets addAdopter(AdoptedPets pet) {
        logger.info("Method was called - addAdopter");
        shelterPetsRepository.findById(pet.getIdPet())
                .orElseThrow(() -> new IdNotFoundException("Информация по питомцу не найдена" + pet.getIdPet()));

        ShelterObject idEntity = shelterPetsRepository.getPetById(pet.getIdPet()).getIdEntity();
        pet.setIdEntity(idEntity);
        return adopterPetsRepository.save(pet);
    }

    /**
     * показать полный список усыновителей
     * @return Collection<AdoptedPets>
     */
    @Override
    public Collection<AdoptedPets> getAllAdopters() {
        logger.info("Method was called - getAllPets");
        Collection<AdoptedPets> adopters = adopterPetsRepository.findAll();
        if (adopters.size() == 0) {
            logger.warn("Method was stopped - getAllPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return adopters;
    }

    /**
     * показать только значения по кошкам
     *
     * @return Collection<AdoptedPets>
     */
    @Override
    public Collection<AdoptedPets> getAllCatAdopters() {
        return getAdoptersByPetType("CAT");
    }

    /**
     * показать только значения по собакам
     *
     * @return Collection<AdoptedPets>
     */
    @Override
    public Collection<AdoptedPets> getAllDogAdopters() {
        return getAdoptersByPetType("DOG");
    }

    @Override
    public AdoptedPets setAdopter(AdoptedPets pet) {
        adopterPetsRepository.findById(pet.getIdPet())
                .orElseThrow(() -> new IdNotFoundException("Информация по идентификатору не найдена" + pet.getIdPet()));
        return adopterPetsRepository.save(pet);
    }

    @Override
    public boolean removeAdopter(Long id) {
        try {
            adopterPetsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn(id + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * @param str строка - название животного
     *            по нему получим идентификатор в БД
     * @return список по данному типу
     */
    private List<AdoptedPets> getAdoptersByPetType(String str) {
        int id_pet = shelterObjectRepository.getIdByText(str);
        List<AdoptedPets> pets = new ArrayList<>();
        for (AdoptedPets p : adopterPetsRepository.findAll())
            if (p.getIdEntity().getIdEntity() == id_pet) {
                pets.add(p);
            }
        return pets;
    }
}