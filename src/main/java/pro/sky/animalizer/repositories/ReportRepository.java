package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
    @Query(value = "SELECT * FROM reports WHERE reports.telegram_id = :telegramId", nativeQuery = true)
    Collection<Report> findByTelegramId(Long telegramId);
}