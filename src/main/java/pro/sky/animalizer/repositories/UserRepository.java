package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalizer.model.User;

/**
 * Интерфейс - репозиторий для работы с User.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}