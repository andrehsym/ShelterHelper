package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.ShelterPets;
import shelterhelper.repository.ShelterPetsRepository;

import java.util.Collection;

@Service
public class ShelterPetsServiceImpl implements ShelterPetsService {
    private final Logger logger = LoggerFactory.getLogger(ShelterPetsServiceImpl.class);
    private final ShelterPetsRepository shelterPetsRepository;

    public ShelterPetsServiceImpl(ShelterPetsRepository shelterPetsRepository) {
        this.shelterPetsRepository = shelterPetsRepository;
    }

    /**
     * добавдяем нового жителя приюта
     *
     * @param pet одна запись для добавления
     * @return одна запись для добавления
     */
    @Override
    public ShelterPets addPet(ShelterPets pet) {
        return shelterPetsRepository.save(pet);
    }

    /**
     * поиск по идентификатору
     * метод может использоваться для проверки актуальности идентификатора
     *
     * @param id идентификатор уникальный
     * @return объект класса
     */
    @Override
    public ShelterPets getPet(Long id) {
        logger.info("Method was called - getPet");
        return shelterPetsRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Информация по идентификатору не найдена" + id));

    }

    /**
     * показать полный список БД
     *
     * @return Collection<ShelterPets>
     */
    @Override
    public Collection<ShelterPets> getAllPets() {
        logger.info("Method was called - getAllPets");
        Collection<ShelterPets> shelterDogs = shelterPetsRepository.findAll();
        if (shelterDogs.size() == 0) {
            logger.warn("Method was stopped - getAllPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterDogs;
    }

    /**
     * Список питомцев из БД, которые уже усыновлены
     * критерии - нвходятсяв текущей БД и is_used = true
     */
    @Override
    public Collection<ShelterPets> getAdoptedPets() {
        logger.info("Method was called - getAdoptedPets");
        Collection<ShelterPets> shelterDogs = shelterPetsRepository.getAdoptedPets();
        if (shelterDogs.size() == 0) {
            logger.warn("Method was stopped - getAdoptedPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterDogs;
    }

    /**
     * Список питомцев из БД, которые на испытательном сроке
     * критерии - нвходятсяв текущей БД
     * нвходятся в БД adopted_dog + is_checking = true
     */
    @Override
    public Collection<ShelterPets> getCheckingDogs() {
        logger.info("Method was called - getCheckingPets");
        Collection<ShelterPets> shelterDogs = shelterPetsRepository.getCheckingDogs();
        if (shelterDogs.size() == 0) {
            logger.warn("Method was stopped - getCheckingPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterDogs;
    }

    @Override
    public Collection<ShelterPets> getCheckingCats() {
        logger.info("Method was called - getCheckingPets");
        Collection<ShelterPets> shelterDogs = shelterPetsRepository.getCheckingCats();
        if (shelterDogs.size() == 0) {
            logger.warn("Method was stopped - getCheckingPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterDogs;
    }

    /**
     * редактирование записи
     *
     * @param id идентификатор питомца
     * @return экземпляр класса
     */
    @Override
    public ShelterPets setPet(Long id) {
        ShelterPets shelterPets = getPet(id);
        return shelterPetsRepository.save(shelterPets);
    }

    /**
     * удаление записи
     *
     * @param id идентификатор питомца
     * @return true - если удаление прошло успешно
     */
    @Override
    public boolean removePet(Long id) {
        try {
            shelterPetsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn(id + e.getMessage());
            return false;
        }
        return true;
    }
}
