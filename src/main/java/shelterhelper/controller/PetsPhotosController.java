package shelterhelper.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.model.AnswerImage;
import shelterhelper.service.PetsPhotosService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/** Эндпоинты для работы с таблицей фотографий питомцев (pets_photos), в которой
 * находятся фотографии всех питомцев приюта
 */

@RestController
public class PetsPhotosController {

    private final PetsPhotosService petsPhotosService;

    public PetsPhotosController(PetsPhotosService petsPhotosService) {
        this.petsPhotosService = petsPhotosService;
    }

    /** Загрузка фотографии питомца с указанием его идентификационного номера
     */
    @PostMapping(value = "/{idPet}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPetPhoto(@PathVariable Long idPet,
                                                    @RequestParam MultipartFile petPhoto) throws IOException {
        petsPhotosService.uploadPetPhoto(idPet, petPhoto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{idPet}/photos")
    public List<ResponseEntity<byte[]>> downloadPhotosByPet(@PathVariable Long idPet) {
//    public ResponseEntity<List<Object>> downloadPhotosByPet(@PathVariable Long idPet) {
//        if (shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet).)
        return petsPhotosService.downloadPhotos(idPet);

//        AnswerImage answerImage = answerImageService.findAnswerImage(answerId);
//        if (answerImage.getMediaType() == null) {
//            return ResponseEntity.notFound().build();
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(answerImage.getMediaType()));
//        headers.setContentLength(answerImage.getData().length);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(answerImage.getData());
    }

    @GetMapping(value = "/photo")
    public ResponseEntity<byte[]> downloadPhotoByNumber(@RequestParam Long idPet, @RequestParam int numberPhotoInOrder) {
        ResponseEntity<byte[]> photo = petsPhotosService.downloadPhotoByNumber(idPet, numberPhotoInOrder);
        if (photo == null) {
            return ResponseEntity.notFound().build();
        }
        return photo;
    }

    @GetMapping(value = "/photos-number")
    public ResponseEntity<HashMap> getNumberOfPhotosForEachPet() {
//        ResponseEntity<HashMap> photoNumber = petsPhotosService.getNumberPhotosEachPet();

        return ResponseEntity.status(HttpStatus.OK).body(petsPhotosService.getNumberPhotosEachPet());
    }

//    @GetMapping(value = "/{idPet}/photos-pet")
//    public ResponseEntity<List> downloadPhotosByPet1(@PathVariable Long idPet) {
////    public ResponseEntity<List<Object>> downloadPhotosByPet(@PathVariable Long idPet) {
////        if (shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet).)
//        return ResponseEntity.status(HttpStatus.OK).body(petsPhotosService.downloadPhotos(idPet));
//    }
}
