package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Интерфейс - репозиторий для работы с сущностью Report.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query(value = "SELECT * FROM reports WHERE reports.telegram_id = :telegramId", nativeQuery = true)
    Collection<Report> findAllByTelegramId(Long telegramId);
    @Query(value = "SELECT * FROM reports WHERE reports.report_date = (SELECT MAX(report_date) FROM reports)", nativeQuery = true)
    Report findLastReportByTelegramId(Long telegramId);
    @Query(value = "SELECT * FROM reports WHERE extract(day from current_date)-extract(day from report_date)>=2", nativeQuery = true)
    Collection<Report> findAllUsersWhoNotSendReport(LocalDate localDate);


}