package shelterhelper.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.model.ShelterPetsPhotos;
import shelterhelper.service.PetsPhotosService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/** Эндпоинты для работы с таблицей фотографий питомцев (pets_photos), в которой
 * находятся фотографии всех питомцев приюта
 */

@RestController
public class PetsPhotosController {

    private final PetsPhotosService petsPhotosService;

    public PetsPhotosController(PetsPhotosService petsPhotosService) {
        this.petsPhotosService = petsPhotosService;
    }

    /**
     * Загрузка фотографии питомца с указанием его идентификационного номера
     */
    @PostMapping(value = "/{idPet}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPetPhoto(@PathVariable Long idPet,
                                                 @RequestParam MultipartFile petPhoto) throws IOException {
        petsPhotosService.uploadPetPhoto(idPet, petPhoto);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить все фотографии питомца (питомец выбирается по его id (idPet))
     */
    @GetMapping(value = "/{idPet}/photos")
    public List<ResponseEntity<byte[]>> downloadPhotosByPet(@PathVariable Long idPet) {
        return petsPhotosService.downloadPhotos(idPet);
    }

    /**
     * Получить одну фотографию питомца указав id питомца и номер фотографии по порядку,
     * если фотографий несколько, например получить вторую фотографию питомца (питомца
     * выбираем по idPet).
     */
    @GetMapping(value = "/photo-by-number-in-order")
    public ResponseEntity<byte[]> downloadPhotoByNumber(@RequestParam Long idPet, @RequestParam int numberPhotoInOrder) {
        ResponseEntity<byte[]> photo = petsPhotosService.downloadPhotoByNumber(idPet, numberPhotoInOrder - 1);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        return photo;
    }
    /**
     * Получить список всех питомцев с указанием количества загруженных фотографий для каждого
     */
    @GetMapping(value = "/pets-with-number-photos")
    public ResponseEntity<HashMap> getNumberOfPhotosForEachPet() {
        return ResponseEntity.status(HttpStatus.OK).body(petsPhotosService.getNumberPhotosEachPet());
    }

    /**
     * Получить фотографию по ее id (idPhoto)
     */
    @GetMapping(value = "/{idPhoto}/photo")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long idPhoto) {
        ShelterPetsPhotos petPhoto = petsPhotosService.findPhotoById(idPhoto);
        if (petPhoto.getMediaType() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(petPhoto.getMediaType()));
        headers.setContentLength(petPhoto.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(petPhoto.getData());
    }

    /**
     * Получить список всех загруженных фотографий
     */
    @GetMapping(value = "/all-photos")
    public List<ResponseEntity<byte[]>> downloadAllPhotos() {
        return petsPhotosService.downloadAll();
    }

}
