package shelterhelper.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * Эндпоинты для работы с таблицей отчетов (reports).
 * Отчеты присылаются усыновителями. С отчетами работают волонтеры.
 * Они оценивают качество отчета, своевременность поступления отчета,
 * наличие фотографии к отчету, количество присланных отчетов .
 * Результатом проверки отчета является установка флага
 * проверки isAccepted: true - отчет принят, false - не принят.
 */

@RestController
public class ReportsController {

}
