package shelterhelper.controller;

import org.springframework.web.bind.annotation.*;
import shelterhelper.model.ShelterPets;
import shelterhelper.service.ShelterPetsService;

import java.util.Collection;

/**
 * 3. В каждом контроллере реализовать эндпоинты для создания, получения, изменения и удаления сущностей,
 * используя все правила формирования REST-запросов: GET-методы для получения данных, POST— для создания…
 */
@RestController
@RequestMapping("/pets")
public class ShelterPetsController {
    private final ShelterPetsService shelterPetsService;

    public ShelterPetsController(ShelterPetsService shelterPetsService) {
        this.shelterPetsService = shelterPetsService;
    }

    /**
     * POST http://localhost:8080/pets
     */
    @PostMapping
    public ShelterPets createPet(@RequestBody ShelterPets pet) {
        return shelterPetsService.addPet(pet);
    }


    /**
     * Удалить  по идентификатору
     * DELETE  http://localhost:8080/pets/2
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
    public Collection<ShelterPets> getAllDogs (@RequestParam(required = false) boolean only_cat,
                                               @RequestParam(required = false) boolean only_dog){
        if (only_cat) {
            return shelterPetsService.getAllCats();
        }
        if (only_dog) {
            return shelterPetsService.getAllDogs();
        }
        return shelterPetsService.getAllPets();
    }
}
