package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalizer.model.Shelter;

/**
 * Интерфейс - репозиторий для работы с сущностью Shelter.
 */
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
