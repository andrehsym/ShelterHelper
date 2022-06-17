package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shelterhelper.model.Reports;

public interface ReportsRepository extends JpaRepository<Reports, Long> {

}
