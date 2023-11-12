package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animalizer.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {
}
