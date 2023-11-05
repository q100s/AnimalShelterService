package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.animalizer.model.User;

/**
 * Интерфейс - репозиторий для работы с User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM Users u WHERE u.telegramId = telegram_id", nativeQuery = true)
    User findUserByTelegramId(@Param("telegram_id") Long telegramId);
}