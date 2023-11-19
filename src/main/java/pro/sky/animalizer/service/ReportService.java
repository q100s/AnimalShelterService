package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.ReportNotFondException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.ReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {

        this.reportRepository = reportRepository;
    }


    public Report createReport(Report report) {
        logger.info("Started createReport method");
        return reportRepository.save(report);
    }

    public Collection<Report> findAllReports(){
        logger.info("Started findAllReport method");
        return reportRepository.findAll();
    }


    public Report findReportById(Long id) {
        logger.info("Started findReportById method");
        return reportRepository.findById(id).orElseThrow(ReportNotFondException::new);
    }

    public Collection<Report> findReportsByTelegramId(Long telegramId) {
        logger.info("start method findUserByTelegramId");
        return reportRepository.findAllByTelegramId(telegramId);
    }

    public Report findLastReportByTelegramId(Long telegramId){
        logger.info("start method findLastReportByTelegramId");
        return reportRepository.findLastReportByTelegramId(telegramId);
    }
    public Collection<Report> getAllUsersWhoNotSendReport(LocalDate localDate) {
        logger.info("start method getAllUsersWhoNotSendReport");
     return reportRepository.findAllUsersWhoNotSendReport(localDate);
    }



}