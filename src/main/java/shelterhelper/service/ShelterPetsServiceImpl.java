package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.ShelterDogs;
import shelterhelper.repository.ShelterDogsRepository;

import java.util.Collection;

@Service
public class ShelterPetsServiceImpl implements ShelterPetsService {
    private final Logger logger = LoggerFactory.getLogger(ShelterPetsServiceImpl.class);
    private final ShelterDogsRepository shelterDogsRepository;

    public ShelterPetsServiceImpl(ShelterDogsRepository shelterDogsRepository) {
        this.shelterDogsRepository = shelterDogsRepository;
    }

    /**
     * добавдяем нового жителя приюта
     *
     * @param pet одна запись для добавления
     * @return одна запись для добавления
     */
    @Override
    public ShelterDogs addPet(ShelterDogs pet) {
        return shelterDogsRepository.save(pet);
    }

    /**
     * поиск по идентификатору
     * метод может использоваться для проверки актуальности идентификатора
     *
     * @param id идентификатор уникальный
     * @return объект класса
     */
    @Override
    public ShelterDogs getPet(Long id) {
        logger.info("Method was called - getPet");
        return shelterDogsRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Информация по идентификатору не найдена" + id));

    }

    /**
     * показать полный список БД
     *
     * @return Collection<ShelterDogs>
     */
    @Override
    public Collection<ShelterDogs> getAllPets() {
        logger.info("Method was called - getAllPets");
        Collection<ShelterDogs> shelterDogs = shelterDogsRepository.findAll();
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
    public Collection<ShelterDogs> getAdoptedPets() {
        logger.info("Method was called - getAdoptedPets");
        Collection<ShelterDogs> shelterDogs = shelterDogsRepository.getAdoptedPets();
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
    public Collection<ShelterDogs> getCheckingPets() {
        logger.info("Method was called - getCheckingPets");
        Collection<ShelterDogs> shelterDogs = shelterDogsRepository.getCheckingPets();
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
    public ShelterDogs setPet(Long id) {
        ShelterDogs shelterDogs = getPet(id);
        return shelterDogsRepository.save(shelterDogs);
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
            shelterDogsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn(id + e.getMessage());
            return false;
        }
        return true;
    }
}
