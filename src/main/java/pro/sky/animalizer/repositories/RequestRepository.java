package pro.sky.animalizer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.animalizer.model.Request;

import java.util.Collection;

/**
 * Репозиторий обращений для работы с БД
 */
public interface RequestRepository extends JpaRepository<Request, Long> {

    /**
     * Подсчет количества обращений пользователя с конретным chat id в телеграмм
     *
     * @param chatId - chat id пользователя в телеграмм
     * @return int - общее число обращений пользователя с конкретным chat id в телеграмм
     */
    @Query(value = "SELECT COUNT(id)  FROM request WHERE chat_id = ?1", nativeQuery = true)
    int countRequestsByChatId(Long chatId);

    /**
     * Выборка из БД всех обращений одного пользователя по его телеграмм chat id
     *
     * @param chatId - уникальный chat id пользователя в телеграмм
     * @return Collection<Request> - список всех запросов пользователя с конкретным телеграмм chat id
     */
    @Query(value = "SELECT *  FROM request WHERE chat_id = ?1", nativeQuery = true)
    Collection<Request> findAllRequestsByChatId(Long chatId);

}


