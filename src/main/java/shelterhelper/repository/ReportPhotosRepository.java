package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shelterhelper.model.ReportPhotos;

import java.util.List;

public interface ReportPhotosRepository extends JpaRepository<ReportPhotos, Long> {
    List<ReportPhotos> findAllByReports_IdReport(Long idReport);
}
