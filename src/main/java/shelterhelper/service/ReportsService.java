package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.AdoptedPets;
import shelterhelper.model.ReportPhotos;
import shelterhelper.model.Reports;
import shelterhelper.repository.AdopterPetsRepository;
import shelterhelper.repository.ReportPhotosRepository;
import shelterhelper.repository.ReportsRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportsService {

    private final Logger logger = LoggerFactory.getLogger(ReportsService.class);
    private ReportPhotosRepository reportPhotosRepository;
    private ReportsRepository reportsRepository;
    private AdopterPetsRepository adopterPetsRepository;

    public ReportsService(ReportPhotosRepository reportPhotosRepository, ReportsRepository reportsRepository, AdopterPetsRepository adopterPetsRepository) {
        this.reportPhotosRepository = reportPhotosRepository;
        this.reportsRepository = reportsRepository;
        this.adopterPetsRepository = adopterPetsRepository;
    }

    /**
     * Добавление или изменение отчета.
     * Отчет можно добавить или изменить только по существующему усыновителю!
     * @param report отчет
     * @return добавленный отчет
     */
    public Reports addReport(Reports report) {
        AdoptedPets adoptedPets = adopterPetsRepository.findById(report.getAdoptedPets().getId())
                .orElseThrow(() -> new IdNotFoundException
                        ("Усыновитель с таким id: " + report.getAdoptedPets().getId() + " не найден"));
        return reportsRepository.save(report);
    }

    /**
     * Находит все отчеты на указанную дату с указанием
     * всех idPhoto по каждому отчету. Информация по каждому отчету
     * выводится отдельной строкой, отформатированной для читабельности.
     * @param date дата
     * @return List<String>
     */
    public List<String> findAllReportsByDateWithPhoto(LocalDate date) {
        List<Reports> reports = reportsRepository.findAllByDateReport(date);
        return transformListReportsToStrings(reports);
    }

    /**
     * Находит все отчеты, поступившие от указанного усыновителя,
     * с указанием всех idPhoto по каждому отчету. Информация по каждому отчету
     * выводится отдельной строкой, отформатированной для читабельности.
     * @param idUser дата
     * @return List<String>
     */
    public List<String> findAllReportsByUserWithPhoto(Long idUser) {
        List<Reports> reports = reportsRepository.findAllByAdoptedPets_IdUser(idUser);
        return transformListReportsToStrings(reports);
    }

    public List<String> findAllReportsByPetWithPhoto(Long idPet) {
        List<Reports> reports = reportsRepository.findAllByAdoptedPets_IdPet(idPet);
        return transformListReportsToStrings(reports);
    }

    public List<String> findAllReportsByDateBetweenWithPhoto(LocalDate date1, LocalDate date2){
        List<Reports> reports = reportsRepository.findAllByDateReportIsBetween(date1, date2);
        return transformListReportsToStrings(reports);
    }

    public String findReportByIdReport(Long idReport) {
        Reports report = reportsRepository.findById(idReport).orElseThrow(()->new IdNotFoundException
                ("Отчет с таким id: " + idReport + " не найден"));
        return transformReportToString(report, false);
    }

    public String findReportByDateAndIdUser(LocalDate date, Long idUser) {
        Reports report = reportsRepository.findReportByDateReportAndAdoptedPetsIdUser(date, idUser);
        return transformReportToString(report, false);
    }

    public String findReportByDateAndIdPet(LocalDate date, Long idPet) {
        Reports report = reportsRepository.findReportByDateReportAndAdoptedPetsIdPet(date, idPet);
        return transformReportToString(report, false);
    }

    public void deleteAll() {
        reportsRepository.deleteAll();
    }

    public void deleteById(Long idReport) {
        reportsRepository.deleteById(idReport);
    }

    public ReportPhotos findReportPhotoById(Long idPhoto) {
       ReportPhotos photo = reportPhotosRepository.findById(idPhoto).orElseThrow(()->new IdNotFoundException
               ("Фотография с таким id: " + idPhoto + " не найдена"));
       return photo;
    }

    /**
     * Получает список значений idPhoto всех фотографий,
     * которые принадлежат данному отчету.
     * @param report отчет
     * @return List - список значений idPhoto
     */
    public List getAllIdPhotoByReport(Reports report) {
        List<ReportPhotos> photos = reportPhotosRepository.findAllByReports_IdReport(report.getIdReport());
        ArrayList<Long> idPhotoList = new ArrayList<>();
        for (ReportPhotos photo : photos) {
            idPhotoList.add(photo.getIdPhoto());
        }
        return idPhotoList;
    }

    /**
     * Трансформация отчета в строку с изменениями и дополнениями
     * для читабельности. Вывод текста отчета (text-report) может быть ограничено
     * количеством знаков или нет, это указывается в параметре textLimit.
     * Для работы с конкретным отчетом надо использовать другой запрос.
     * @param report отчет
     * @param textLimit ограничение текста отчета - да или нет
     * @return String - отчет в виде строки
     */
    public String transformReportToString(Reports report, boolean textLimit) {
        int numberOfCharacters = 10;
        if (report.getTextReport().length() < 12 | !textLimit)  {
            numberOfCharacters = report.getTextReport().length();
        }
        String descriptionReport = "<idReport>= " + report.getIdReport()
                + ", <dateReport>= " + report.getDateReport()
                + ", <idUser>= " + report.getAdoptedPets().getIdUser()
                + ", <idPet>= " + report.getAdoptedPets().getIdPet()
                + " " + report.getAdoptedPets().getIdEntity().getTextEntity()
                + ", <isAccepted>= " + report.isAccepted()
                + ", <textReport>: '" + report.getTextReport().substring(0, numberOfCharacters)
                + "...', <idPhotos>:" + getAllIdPhotoByReport(report);

        return descriptionReport;
    }

    /**
     * Преобразование списка отчетов в список отформатированных
     * строк для читабельности.
     * @param reports отчет
     * @return String - отчет в виде строки
     */
    public List<String> transformListReportsToStrings(List<Reports> reports) {
        if (reports.size() == 0) {
            Throwable throwable = new IdNotFoundException("Отчетов по данному запросу не найдено");
        }
        ArrayList<String> reportsTransform = new ArrayList<>();
        for (Reports report : reports) {
            reportsTransform.add(transformReportToString(report, true));
        }
        return reportsTransform;
    }

    public List<Reports> findAllReportsByDate(LocalDate date){
        return reportsRepository.findAllByDateReport(date);
    }

    public List<Reports> findAllReportsByDateBetween(LocalDate date1, LocalDate date2){
        return reportsRepository.findAllByDateReportIsBetween(date1, date2);
    }
}
