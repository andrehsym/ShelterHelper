package shelterhelper.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelterhelper.model.ReportPhotos;
import shelterhelper.model.Reports;
import shelterhelper.model.ShelterPetsPhotos;
import shelterhelper.service.ReportsService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Эндпоинты для работы с таблицей отчетов (reports).
 * Отчеты присылаются усыновителями. С отчетами работают волонтеры.
 * Они оценивают качество отчета, своевременность поступления отчета,
 * наличие фотографии к отчету, количество присланных отчетов .
 * Результатом проверки отчета является установка флага
 * проверки isAccepted: true - отчет принят, false - не принят.
 */

@RestController
@RequestMapping("/report")
public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    /**
     * POST http://localhost:8080/report
     * Создание или изменение отчета. Может использоваться для тестирования.
     * При работе приложения отчеты поступают из telegramBot.
     * @param report отчет
     * @return отчет добавленный или измененный в формате json
     */
    @PostMapping
    public Reports createReport(@RequestBody Reports report) {
        return reportsService.addReport(report);
    }

    /**
     * POST http://localhost:8080/report/all-by
     * Общий запрос на получение списка отчетов, отобранных в зависимости от
     * указанных параметров. Текст отчета в каждом отчете ограничен 10 знаками
     * для читабельности.  Для работы с конкретным отчетом надо использовать другой запрос.
     * Параметры указаны по приоритету исполнения:
     *  - если указан idUser - возвращаются все отчеты c указанным пользователем;
     *  - если указан idPet - возвращаются все отчеты c указанным питомцем;
     *  - если указана только одна из двух дат (date1 или date2) - возвращаются все отчеты на эту дату;
     *  - если указаны обе даты - возвращаются все отчеты с датами в диапазоне: date1-date2.
     * @param idUser id пользователя-усыновителя
     * @param idPet id питомца (усыновленного)
     * @param date1 дата начала диапазона или дата поиска
     * @param date2 дата конца диапазона или дата поиска
     * @return List<String> - список отчетов в формате строк
     */
    @GetMapping("/all-by")
    public ResponseEntity<List<String>> getAllBy
            (@RequestParam(required = false) Long idUser,
             @RequestParam(required = false) Long idPet,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        if (idUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByUserWithPhoto(idUser));
        }
        if (idPet != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByPetWithPhoto(idPet));
        }
        if (date1 != null & date2 == null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateWithPhoto(date1));
        }
        if (date1 == null & date2 != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateWithPhoto(date2));
        }
        if (date1 != null & date2 != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateBetweenWithPhoto(date1, date2));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * POST http://localhost:8080/report/by
     * Общий запрос на получение отчета в формате строки, выбранного
     * в зависимости от указанных параметров.
     * Параметры указаны по приоритету исполнения:
     *  - если указан idUser - возвращаются все отчеты c указанным пользователем;
     *  - если указан idPet - возвращаются все отчеты c указанным питомцем;
     *  - если указана только одна из двух дат (date1 или date2) - возвращаются все отчеты на эту дату;
     *  - если указаны обе даты - возвращаются все отчеты с датами в диапазоне: date1-date2.
     * @param idReport id пользователя-усыновителя
     * @param date дата начала диапазона или дата поиска
     * @param idUser id пользователя-усыновителя
     * @param idPet id питомца (усыновленного)
     * @return String - отчет в формате строки
     */
    @GetMapping("/by")
    public ResponseEntity<String> getReportBy
    (@RequestParam(required = false) Long idReport,
     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
     @RequestParam(required = false) Long idUser,
     @RequestParam(required = false) Long idPet) {
        if (idReport != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findReportByIdReport(idReport));
        }
        if (idUser != null & date != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findReportByDateAndIdUser(date, idUser));
        }
        if (idPet != null & date != null) {
            return ResponseEntity.status(HttpStatus.OK).body(reportsService.findReportByDateAndIdPet(date, idPet));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Запрос на получение фотографии по ее идентификатору - idPhoto
     * @param idPhoto идентификатор фотографии
     * @return фотографию
     */
    @GetMapping(value = "/{idPhoto}")
    public ResponseEntity<byte[]> downloadReportPhotoBeId(@PathVariable Long idPhoto) {
        ReportPhotos reportPhoto = reportsService.findReportPhotoById(idPhoto);
        if (reportPhoto.getMediaType() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(reportPhoto.getMediaType()));
        headers.setContentLength(reportPhoto.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(reportPhoto.getData());
    }

    /**
     * Запрос на удаление отчетов. Если задан критерий на удаление всех отчетов all = true,
     * тогда удаляются все отчеты, если all = false (установлено по умолчанию) и указан
     * идентификатор отчета (idReport), тогда удаляется один отчет с указанным id
     * @param idReport идентификатор отчета
     * @param all параметр, указывающий на удаление всех отчетов
     * @return результат операции удаления
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ShelterPetsPhotos> delete(@RequestParam(required = false) Long idReport,
                                                    @RequestParam(required = false) boolean all) {
        if (all) {
            reportsService.deleteAll();
            return ok().build();
        }
        if (idReport != null) {
            reportsService.deleteById(idReport);
            return ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
