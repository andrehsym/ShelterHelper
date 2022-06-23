package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shelterhelper.model.Reports;

import java.time.LocalDate;
import java.util.List;

public interface ReportsRepository extends JpaRepository<Reports, Long> {

    List<Reports> findAllByDateReportIsBetween(LocalDate date1, LocalDate date2);

    List<Reports> findAllByDateReport(LocalDate date);

    List<Reports> findAllByAdoptedPets_IdUser(Long idUser);

    List<Reports> findAllByAdoptedPets_IdPet(Long idPet);

}
