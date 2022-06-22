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
import java.util.TreeMap;

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
     * Добавление отчета.
     * Отчет можно добавить только по существующему усыновителю!
     * @param report отчет
     * @return добавленный отчет
     */
    public Reports addReport(Reports report) {
        AdoptedPets adoptedPets = adopterPetsRepository.findById(report.getAdoptedPets().getId())
                .orElseThrow(() -> new IdNotFoundException
                        ("Усыновитель с таким id: " + report.getAdoptedPets().getId() + " не найден"));
        return reportsRepository.save(report);
    }

//    public List<Reports> findAllReportsByDate(LocalDate date){
//        return reportsRepository.findAllByDateReport(date);
//    }
    /**
     * Находит все отчеты на указанную дату с указанием
     * всех idPhoto по каждому отчету. Информация по каждому отчету
     * выводится отдельной строкой, отформатированной для читабельности.
     * @param date дата
     * @return List<String>
     */
    public List<String> findAllReportsByDateWithPhoto(LocalDate date) {
        List<Reports> reports = reportsRepository.findAllByDateReport(date);
        if (reports.size() == 0) {
            Throwable throwable = new IdNotFoundException("Отчетов нет на эту дату");
        }
        ArrayList<String> reportsTransform = new ArrayList<>();
        for (Reports report : reports) {
            reportsTransform.add(transformReportToString(report));
        }
        return reportsTransform;
    }

    public List<Reports> findAllReportsByDateBetween(LocalDate date1, LocalDate date2){
        return reportsRepository.findAllByDateReportIsBetween(date1, date2);
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
     * Трансформация отчета в строку с изменениями и дополнениями для читабельности.
     * @param report отчет
     * @return String - отчет в виде строки
     */
    public String transformReportToString(Reports report) {
        int numberOfCharacters = 10;
        if (report.getTextReport().length() < 12) {
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
}
