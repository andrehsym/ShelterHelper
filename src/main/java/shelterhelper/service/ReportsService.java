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

       public List<Reports> findAllReportsByDateBetween(LocalDate date1, LocalDate date2){
           return reportsRepository.findAllByDateReportIsBetween(date1, date2);
       }
    /**
     * Добавление отчета.
     * Отчет можно добавить только по существующему усыновителю!
     * @param report экземпляр
     * @return экземпляр класса
     */
    public Reports addReport(Reports report) {
        AdoptedPets adoptedPets = adopterPetsRepository.findById(report.getAdoptedPets().getId())
                .orElseThrow(() -> new IdNotFoundException
                        ("Усыновитель с таким id: " + report.getAdoptedPets().getId() + " не найден"));
        return reportsRepository.save(report);
    }

    public List<Reports> findAllReportsByDate(LocalDate date){
        return reportsRepository.findAllByDateReport(date);
    }

    public List findAllReportsByDateWithPhoto(LocalDate date) {
        List<Reports> reports = reportsRepository.findAllByDateReport(date);
        if (reports.size() == 0) {
            Throwable throwable = new IdNotFoundException("Отчетов нет");
        }
        String descriptionReport;
        ArrayList<String> reportsList = new ArrayList<>();
        List<ReportPhotos> photos = new ArrayList<>();
        for (Reports report : reports) {
            photos = reportPhotosRepository.findAllByReports_IdReport(report.getIdReport());
            ArrayList<Long> idPhotoList = new ArrayList<>();
            for (ReportPhotos photo : photos) {
                idPhotoList.add(photo.getIdPhoto());
            }
            descriptionReport = "idReport=" + report.getIdReport()
                    + ", dateReport=" + report.getDateReport()
                    + ", idUser=" + report.getAdoptedPets().getIdUser()
                    + ", idPet=" + report.getAdoptedPets().getIdPet()
                    + report.getAdoptedPets().getIdEntity().getTextEntity()
                    + ", isAccepted=" + report.isAccepted() + ", textReport: " + report.getTextReport()
                    + ", idPhotos:" + idPhotoList;
            reportsList.add(descriptionReport);
        }
        return reportsList;
    }
}
