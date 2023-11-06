package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.animalizer.model.User;

/**
 * Интерфейс - репозиторий для работы с User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT users FROM users WHERE users.telegram_id = :telegramId", nativeQuery = true)
    User findByTelegramId(Long telegramId);
}