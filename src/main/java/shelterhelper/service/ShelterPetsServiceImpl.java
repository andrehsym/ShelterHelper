package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.ShelterPets;
import shelterhelper.repository.ShelterObjectRepository;
import shelterhelper.repository.ShelterPetsRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ShelterPetsServiceImpl implements ShelterPetsService {
    private final Logger logger = LoggerFactory.getLogger(ShelterPetsServiceImpl.class);
    private final ShelterPetsRepository shelterPetsRepository;
    private final ShelterObjectRepository shelterObjectRepository;

    public ShelterPetsServiceImpl(ShelterPetsRepository shelterPetsRepository, ShelterObjectRepository shelterObjectRepository) {
        this.shelterPetsRepository = shelterPetsRepository;
        this.shelterObjectRepository = shelterObjectRepository;
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
     * Список питомцев из БД, которые уже усыновлены
     * критерии - нвходятсяв текущей БД и is_used = true
     */
    @Override
    public Collection<ShelterPets> getAdoptedPets() {
        logger.info("Method was called - getAdoptedPets");
        Collection<ShelterPets> shelterPets = shelterPetsRepository.getAdoptedPets();
        if (shelterPets.size() == 0) {
            logger.warn("Method was stopped - getAdoptedPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterPets;
    }

    /**
     * Список питомцев из БД, которые на испытательном сроке
     * критерии - нвходятсяв текущей БД
     * нвходятся в БД adopted_dog + is_checking = true
     */
    @Override
    public Collection<ShelterPets> getCheckingPets() {
        logger.info("Method was called - getCheckingPets");
        Collection<ShelterPets> shelterPets = shelterPetsRepository.getCheckingPets();
        if (shelterPets.size() == 0) {
            logger.warn("Method was stopped - getCheckingPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterPets;

    }

    /**
     * Список только кошек или только собак  из БД, которые на испытательном сроке
     * критерии - нвходятсяв текущей БД
     * нвходятся в БД adopted_dog + is_checking = true
     */

    @Override
    public Collection<ShelterPets> getCheckingDogs() {
        logger.info("Method was called - getCheckingDogs");
        return getCheckingPetsByType("DOG");
    }

    @Override
    public Collection<ShelterPets> getCheckingCats() {
        logger.info("Method was called - getCheckingCats");
        return getCheckingPetsByType("CAT");
    }

    /**
     * @param pet запись для редактирования
     * @return ShelterPets
     */
    @Override
    public ShelterPets setPet(ShelterPets pet) {
        return shelterPetsRepository.save(pet);
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

    /**
     * показать полный список БД
     *
     * @return Collection<ShelterPets>
     */
    @Override
    public Collection<ShelterPets> getAllPets() {
        logger.info("Method was called - getAllPets");
        Collection<ShelterPets> shelterPets = shelterPetsRepository.findAll();
        if (shelterPets.size() == 0) {
            logger.warn("Method was stopped - getAllPets");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shelterPets;
    }

    /**
     * список питомцев. Только коше или только собак
     * БД сущностей содержит только нормализованные записи
     *
     * @return List<ShelterPets>
     */
    @Override
    public List<ShelterPets> getAllCats() {
        return getPetsByType("CAT");
    }

    @Override
    public List<ShelterPets> getAllDogs() {
        return getPetsByType("DOG");
    }

    private List<ShelterPets> getPetsByType(String str) {
        int id_pet = shelterObjectRepository.getIdByText(str);
        return shelterPetsRepository.findAll()
                .stream()
                .filter(p -> p.getIdEntity().getIdEntity() == id_pet)
                .collect(Collectors.toList());
    }

    private List<ShelterPets> getCheckingPetsByType(String str) {
        int id_pet = shelterObjectRepository.getIdByText(str);
        return shelterPetsRepository.getCheckingPets()
                .stream()
                .filter(p -> p.getIdEntity().getIdEntity() == id_pet)
                .collect(Collectors.toList());
    }
}
