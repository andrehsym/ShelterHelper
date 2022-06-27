package shelterhelper.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import shelterhelper.model.AdoptedPets;
import shelterhelper.service.ShelterAdopterPetsService;

import java.util.Collection;

/**
 * 3. В каждом контроллере реализовать эндпоинты для создания, получения, изменения и удаления сущностей,
 * используя все правила формирования REST-запросов: GET-методы для получения данных, POST— для создания…
 */
@RestController
@RequestMapping("/adopter")
public class ShelterAdopterPetsController {
    public final ShelterAdopterPetsService shelterAdopterPetsService;

    public ShelterAdopterPetsController(@Qualifier("adopterPetsService")
                                                ShelterAdopterPetsService shelterAdopterPetsService) {
        this.shelterAdopterPetsService = shelterAdopterPetsService;
    }

    /**
     * POST http://localhost:8080/adopter
     * создание записи
     */
    @PostMapping
    public AdoptedPets createAdopter(@RequestBody AdoptedPets pet) {
        return shelterAdopterPetsService.addAdopter(pet);
    }

    /**
     * Удалить  по идентификатору
     * DELETE  http://localhost:8080/adopter/2
     * если удаление прошло успешно - TRUE
     */
    @DeleteMapping("{id}")
    public boolean deleteAdopter(@PathVariable long id) {
        return shelterAdopterPetsService.removeAdopter(id);
    }

    /**
     * общий справочный запрос по усыновителям
     * если критерии не заданы- показать всех
     * если задан критерий - показать  только кошек
     * если задан критерий - показать только собак
     */
    @GetMapping("/all")
    public Collection<AdoptedPets> getCheckingPets(@RequestParam(required = false) boolean only_cat,
                                                   @RequestParam(required = false) boolean only_dog) {
        if (only_cat) {
            return shelterAdopterPetsService.getAllCatAdopters();
        }
        if (only_dog) {
            return shelterAdopterPetsService.getAllDogAdopters();
        }
        return shelterAdopterPetsService.getAllAdopters();
    }

    /**
     * PUT http://localhost:8080/student
     */
    @PutMapping
    public AdoptedPets updateAdopter(@RequestBody AdoptedPets pet) {
        return shelterAdopterPetsService.setAdopter(pet);
    }


}
