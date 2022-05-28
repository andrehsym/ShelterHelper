package shelterhelper.controller;

import org.springframework.web.bind.annotation.*;
import shelterhelper.model.ShelterDogs;
import shelterhelper.service.ShelterPetsService;

import java.util.Collection;

/**
 * 3. В каждом контроллере реализовать эндпоинты для создания, получения, изменения и удаления сущностей,
 * используя все правила формирования REST-запросов: GET-методы для получения данных, POST— для создания…
 */
@RestController
@RequestMapping("/dogs")
public class ShelterPetsController {
    private final ShelterPetsService shelterPetsService;

    public ShelterPetsController(ShelterPetsService shelterPetsService) {
        this.shelterPetsService = shelterPetsService;
    }

    /**
     * POST http://localhost:8080/dogs
     */
    @PostMapping
    public ShelterDogs createPet(@RequestBody ShelterDogs pet) {
        return shelterPetsService.addPet(pet);
    }

    /**
     * найти по идентификатору
     * GET http://localhost:8080/dog/1
     */
    @GetMapping("{id}")
    public ShelterDogs getPet(@PathVariable long id) {
        return shelterPetsService.getPet(id);
    }

    /**
     * Удалить  по идентификатору
     * DELETE  http://localhost:8080/dog/2
     * если удаление прошло успешно - TRUE
     */
    @DeleteMapping("{id}")
    public boolean deletePet(@PathVariable long id) {
        return shelterPetsService.removePet(id);
    }

    /**
     * общий справочный запрос
     * если критерии не заданы- показать всех
     * если задан критерий - показать показать только усыновленных
     * если задан критерий - показать показать всех на испытательном сроке
     */
    @GetMapping
    public Collection<ShelterDogs> getallDogs(@RequestParam(required = false) boolean is_used,
                                              @RequestParam(required = false) boolean is_checked) {
        if (is_used) {
            return shelterPetsService.getAdoptedPets();
        }
        if (is_checked) {
            return shelterPetsService.getCheckingPets();
        }
        return shelterPetsService.getAllPets();
    }
}
