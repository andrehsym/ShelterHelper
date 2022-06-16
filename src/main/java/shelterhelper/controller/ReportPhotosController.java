package shelterhelper.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.model.ReportPhotos;
import shelterhelper.model.ShelterPetsPhotos;
import shelterhelper.service.ReportPhotosService;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Эндпоинты для работы с таблицей фотографий (report_photos).
 * Эти фотографии являются частью отчетов, присылаемых усыновителями.
 * Фотографии поступают из telegramBot. Большинство эндпоинтов созданы
 * для тестирования.
 */

@RestController
public class ReportPhotosController {

    private final ReportPhotosService reportPhotosService;

    public ReportPhotosController(ReportPhotosService reportPhotosService) {
        this.reportPhotosService = reportPhotosService;
    }

    /**
     * Для тестирования - загрузка фотографии питомца с указанием идентификатора отчета, к
     * которому эта фотография относится.
     */
    @PostMapping(value = "/photo/{idReport}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadReportPhoto(@PathVariable Long idReport,
                                                 @RequestParam MultipartFile reportPhoto) throws IOException {
        reportPhotosService.uploadReportPhoto(idReport, reportPhoto);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить фотографию по ее id (idPhoto)
     */
    @GetMapping(value = "/photo/{idPhoto}")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long idPhoto) {
        ReportPhotos reportPhoto = reportPhotosService.findPhotoById(idPhoto);
        if (reportPhoto.getMediaType() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(reportPhoto.getMediaType()));
        headers.setContentLength(reportPhoto.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(reportPhoto.getData());
    }

    /**
     * Запрос на удаление фотографий. Если задан критерий на удаление всех фото all = true,
     * тогда удаляются все фото, если all = false (установлено по умолчанию) и указан
     * идентификатор фото (idPhoto), тогда удаляется одна фотография с указанным id
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ShelterPetsPhotos> delete(@RequestParam Long idPhoto,
                                                    @RequestParam(required = false) boolean all) {
        if (all) {
            reportPhotosService.deleteAll();
            return ok().build();
        }
        reportPhotosService.deletePhoto(idPhoto);
        return ok().build();
    }
}
