package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animalizer.model.Pet;

/**
 * Интерфейс - репозиторий для работы с сущностью Pet.
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
