package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.ReportPhotos;
import shelterhelper.model.Reports;
import shelterhelper.repository.ReportPhotosRepository;
import shelterhelper.repository.ReportsRepository;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class ReportPhotosService {

    private final Logger logger = LoggerFactory.getLogger(ReportPhotosService.class);
    private ReportPhotosRepository reportPhotosRepository;
    private ReportsRepository reportsRepository;

    public ReportPhotosService(ReportPhotosRepository reportPhotosRepository, ReportsRepository reportsRepository) {
        this.reportPhotosRepository = reportPhotosRepository;
        this.reportsRepository = reportsRepository;
    }

    public ReportPhotos findPhotoById(Long idPhoto) {
        ReportPhotos reportPhoto = reportPhotosRepository.findById(idPhoto).orElse(new ReportPhotos());
        return reportPhoto;
    }

    public void uploadReportPhoto(Long idReport, MultipartFile reportPhotosFile) throws IOException {
        logger.info("Method was called - uploadReportPhoto");
        Reports reports = reportsRepository.findById(idReport).
                orElseThrow(() -> new IdNotFoundException
                        ("Такого идентификатора в таблице reports не существует" + idReport));
        ReportPhotos reportPhotos = new ReportPhotos();
        reportPhotos.setReports(reports);
        reportPhotos.setFileSize(reportPhotosFile.getSize());
        reportPhotos.setMediaType(reportPhotosFile.getContentType());
        reportPhotos.setData(reportPhotosFile.getBytes());
        reportPhotosRepository.save(reportPhotos);
    }

    public void deleteAll() {
        reportPhotosRepository.deleteAll();
    }

    public void deletePhoto(Long idPhoto) {
        ReportPhotos photo = reportPhotosRepository.findById(idPhoto)
                .orElseThrow(()-> new IdNotFoundException("Такого идентификатора в таблице report_photos не существует" + idPhoto));
        reportPhotosRepository.deleteById(idPhoto);
    }
}
