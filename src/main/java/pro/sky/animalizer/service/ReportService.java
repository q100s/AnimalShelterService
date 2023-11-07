package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.ReportNotFondException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.repositories.ReportRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report){
        logger.info("Started createReport method");
        return reportRepository.save(report);
    }

    public Collection<Report>findAllReport(){
        logger.info("Started findAllReport method");
        return reportRepository.findAll();
    }


    public Report findReportById(long id) {
        logger.info("Started findReportById method");
        return reportRepository.findById(id).orElseThrow(ReportNotFondException::new);
    }
}
