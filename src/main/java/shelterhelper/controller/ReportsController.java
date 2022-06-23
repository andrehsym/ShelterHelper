package shelterhelper.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelterhelper.model.Reports;
import shelterhelper.service.ReportsService;

import java.time.LocalDate;
import java.util.List;

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
     * Создание отчета. Может использоваться для тестирования.
     * При работе приложения отчеты поступают из telegramBot.
     */
    @PostMapping
    public Reports createReport(@RequestBody Reports report) {
        return reportsService.addReport(report);
    }

    /**
     * POST http://localhost:8080/report/all-by
     * Общий запрос на получение списка отчетов, отобранных в зависимости от
     * указанных параметров. Параметры указаны по приоритету исполнения:
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

//    @GetMapping("/all-between-dates")
//    public ResponseEntity<List<Reports>> getAllReportsForTime(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
//        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateBetween(date1, date2));
//    }
//
//    /**
//     * Получить все отчеты на определенную дату.
//     * Возвращается список отчетеов в формате json.
//     */
//    @GetMapping("/all-by-date")
//    public ResponseEntity<List<Reports>> getAllReportsByDate(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        if (date == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDate(date));
//    }
//
//    /**
//     * Получить все отчеты на определенную дату с информацией по фотографиям.
//     * Возвращается список отчетеов в формате string.
//     */
//    @GetMapping("/all-by-date-photo")
//    public ResponseEntity<List<String>> getAllReportsByDateWithPhoto(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateWithPhoto(date));
//    }
}
