package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.ShelterPets;
import shelterhelper.model.ShelterPetsPhotos;
import shelterhelper.repository.ShelterObjectRepository;
import shelterhelper.repository.ShelterPetsPhotosRepository;
import shelterhelper.repository.ShelterPetsRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class PetsPhotosService {

    private final Logger logger = LoggerFactory.getLogger(PetsPhotosService.class);
    private ShelterPetsPhotosRepository shelterPetsPhotosRepository;
    private ShelterPetsRepository shelterPetsRepository;
    private ShelterObjectRepository shelterObjectRepository;

    public PetsPhotosService(ShelterPetsPhotosRepository shelterPetsPhotosRepository, ShelterPetsRepository shelterPetsRepository, ShelterObjectRepository shelterObjectRepository) {
        this.shelterPetsPhotosRepository = shelterPetsPhotosRepository;
        this.shelterPetsRepository = shelterPetsRepository;
        this.shelterObjectRepository = shelterObjectRepository;
    }

    public void uploadPetPhoto(Long idPet, MultipartFile answerImageFile) throws IOException {
        logger.info("Method was called - uploadPetPhoto");
        ShelterPets shelterPets = shelterPetsRepository.findById(idPet).
                orElseThrow(() -> new IdNotFoundException
                        ("Такого идентификатора в таблице answer не существует" + idPet));
        ShelterPetsPhotos shelterPetsPhotos = new ShelterPetsPhotos();
        shelterPetsPhotos.setShelterPets(shelterPets);
        shelterPetsPhotos.setFileSize(answerImageFile.getSize());
        shelterPetsPhotos.setMediaType(answerImageFile.getContentType());
        shelterPetsPhotos.setData(answerImageFile.getBytes());
        shelterPetsPhotosRepository.save(shelterPetsPhotos);
    }

//    public AnswerImage findAnswerImage(Long answerId) {
//        AnswerImage answerImage = answerImageRepository.findByAnswer_Id(answerId).orElse(new AnswerImage());
//        return answerImage;
//    }

    public List<ResponseEntity<byte[]>> downloadPhotos(Long idPet) {
        List<ShelterPetsPhotos> photos = shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet);
        if (photos.size() == 0) {
            Throwable throwable = new IdNotFoundException("Такого идентификатора в таблице answer не существует" + idPet);
        }
        ArrayList<ResponseEntity<byte[]>> listPhotos = new ArrayList<ResponseEntity<byte[]>>();
        for (ShelterPetsPhotos photoPet:photos) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(photoPet.getMediaType()));
            headers.setContentLength(photoPet.getData().length);
            listPhotos.add(ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoPet.getData()));
        }
        return listPhotos;
    }

    public ResponseEntity<byte[]> downloadPhotoByNumber(Long idPet, int num) {
        List<ShelterPetsPhotos> photos = shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet);
        if (photos.size() == 0) {
            Throwable throwable = new IdNotFoundException("Такого идентификатора в таблице answer не существует" + idPet);
        }
        ArrayList<ResponseEntity<byte[]>> listPhotos = new ArrayList<ResponseEntity<byte[]>>();
        for (ShelterPetsPhotos photoPet:photos) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(photoPet.getMediaType()));
            headers.setContentLength(photoPet.getData().length);
            listPhotos.add(ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoPet.getData()));
        }
        try {
            ResponseEntity<byte[]> photoByNumber = listPhotos.get(num);
        }catch (RuntimeException e){
            return null;
        }
        ResponseEntity<byte[]> photoByNumber = listPhotos.get(num);
        return photoByNumber;
    }

//    public HashMap<ShelterPets,Integer> getNumberPhotosEachPet1() {
//        List<ShelterPets> petsList = shelterPetsRepository.findAll();
//        HashMap<ShelterPets, Integer> numberPhotosPet= new HashMap<>();
//        Integer numberPhotos = 0;
//        for (ShelterPets pet : petsList) {
//            List<ShelterPetsPhotos> photos =
//                    shelterPetsPhotosRepository.findAllByShelterPets_IdPet(pet.getIdPet());
//            numberPhotos = photos.size();
//            numberPhotosPet.put(pet, numberPhotos);
//        }
//        return numberPhotosPet;
//    }

    public HashMap<String,Integer> getNumberPhotosEachPet() {
        List<ShelterPets> petsList = shelterPetsRepository.findAll();
        HashMap<String, Integer> numberPhotosPet= new HashMap<>();
        Integer numberPhotos = 0;
        String descriptionPet;
        for (ShelterPets pet : petsList) {
            List<ShelterPetsPhotos> photos =
                    shelterPetsPhotosRepository.findAllByShelterPets_IdPet(pet.getIdPet());
            numberPhotos = photos.size();
            descriptionPet = "idPet=" + pet.getIdPet() + ", " + pet.getIdEntity().getTextEntity()
                    + ", name: " + pet.getPetName() + " - количество загруженных фото";
            numberPhotosPet.put(descriptionPet, numberPhotos);
        }
        return numberPhotosPet;
    }

//    public Set<ResponseEntity<byte[]>> downloadPhotos1(Long idPet) {
//        List<ShelterPetsPhotos> photos = shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet);
//        if (photos.size() == 0) {
//            Throwable throwable = new IdNotFoundException("Такого идентификатора в таблице answer не существует" + idPet);
//        }
//        Set<ResponseEntity<byte[]>> listPhotos = new HashSet<>();
//        for (ShelterPetsPhotos photoPet:photos) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(photoPet.getMediaType()));
//            headers.setContentLength(photoPet.getData().length);
//            listPhotos.add(ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoPet.getData()));
//        }
//        return listPhotos;
//    }

//    public List<byte[]> downloadPhotos(Long idPet) {
//        List<ShelterPetsPhotos> photos = shelterPetsPhotosRepository.findAllByShelterPets_IdPet(idPet);
//        if (photos.size() == 0) {
//            Throwable throwable = new IdNotFoundException("Такого идентификатора в таблице answer не существует" + idPet);
////            return null;
//        }
//        ArrayList<byte[]> listPhotos = new ArrayList<byte[]>();
//        for (ShelterPetsPhotos photoPet:photos) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(photoPet.getMediaType()));
//            headers.setContentLength(photoPet.getData().length);
////            listPhotos.add(ResponseEntity.status(HttpStatus.OK).headers(headers).body(photoPet.getData()));
//            listPhotos.add(ResponseEntity.of(photoPet.getData().));
//        }
//        return listPhotos;
//    }

}

