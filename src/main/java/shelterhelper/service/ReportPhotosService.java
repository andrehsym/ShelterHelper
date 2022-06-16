package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelterhelper.model.Reports;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReportPhotosService {

    private final Logger logger = LoggerFactory.getLogger(ReportPhotosService.class);
    private ReportPhotosService reportPhotosService;
    private Reports reports;

    public ReportPhotosService(ReportPhotosService reportPhotosService, Reports reports) {
        this.reportPhotosService = reportPhotosService;
        this.reports = reports;
    }

}
