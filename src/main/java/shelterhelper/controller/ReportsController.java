package shelterhelper.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelterhelper.model.AdoptedPets;
import shelterhelper.model.Reports;
import shelterhelper.service.ReportsService;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

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

    @GetMapping("/all-for-time")
    public ResponseEntity<List<Reports>> getAllReportsForTime(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateBetween(date1, date2));
    }

    /**
     * Получить все отчеты на определенную дату.
     * Возвращается список отчетеов в формате json.
     */
    @GetMapping("/all-by-date")
    public ResponseEntity<List<Reports>> getAllReportsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDate(date));
    }

    /**
     * Получить все отчеты на определенную дату с информацией по фотографиям.
     * Возвращается список отчетеов в формате string.
     */
    @GetMapping("/all-by-date-photo")
    public ResponseEntity<List<String>> getAllReportsByDateWithPhoto(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK).body(reportsService.findAllReportsByDateWithPhoto(date));
    }
}
