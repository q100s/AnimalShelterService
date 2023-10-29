package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.Request;
import pro.sky.animalizer.repositories.RequestRepository;

import java.util.Collection;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Класс с методами работы с обращениями пользователей
 * использует репозиторий - базовые методы JpsRepository и доопределенные методы, включая sql запросы
 */
@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    /**
     * Сохранения обращения пользователя в БД
     * Использует метод репозитория  {@link JpaRepository#save(Object)}
     *
     * @param request - обращение к записи в БД
     * @return обращение - результат записи в БД
     */
    public Request saveRequest(Request request) {
        logger.info("Was invoked method for save request");
        return requestRepository.save(request);
    }

    /**
     * Удаление обращения пользователя в БД по id обращения
     * Использует метод репозитория  {@link JpaRepository#deleteById(Object)}
     *
     * @param id
     */
    public void deleteRequest(Long id) {
        logger.info("Was invoked method for delete request by id" + id);
        requestRepository.deleteById(id);
    }

    /**
     * Сохранение изменноего обращения пользователя в БД
     * Использует метод репозитория  {@link JpaRepository#save(Object)}
     *
     * @param request - изменное обращение к записи в БД
     * @return обращение - результат записи в БД
     */
    public Request editRequest(Request request) {
        logger.info("Was invoked method for edit request");
        return requestRepository.save(request);
    }

    /**
     * Получение всех обращений из БД
     * Использует метод репозитория  {@link JpaRepository#findAll()}
     *
     * @return список всех обращей, всех пользователей из БД
     */
    public Collection<Request> getAllRequests() {
        logger.info("Was invoked method for find all requests");
        return requestRepository.findAll();
    }

    /**
     * Получение всех обращений из БД конкретного пользователя по chat id в телеграмм
     * Использует метод репозитория  {@link RequestRepository#findAllRequestsByChatId(Long)}
     *
     * @param chatId - chat id пользователя в телеграмм
     * @return список всех обращей, всех пользователей из БД
     * @return Collection<Request> - список всех обращений пользователя с конкретным chat id
     */
    public Collection<Request> getAllRequestsByChatId(Long chatId) {
        logger.info("Was invoked method for find all requests by tg_chat_id");
        return requestRepository.findAllRequestsByChatId(chatId);
    }

    /**
     * Проверка наличия обращений от пользователя - новый или не новый пользователь
     * Использует метод репозитория  {@link RequestRepository#countRequestsByChatId(Long)}
     *
     * @param chatId - chat id пользователя в телеграмм
     * @return - TRUE если пользователь еще не обращался (новый), FALSE - если пользователь уже обращался
     */
    public boolean checkIfNewUser(Long chatId) {
        logger.info("Was invoked method for check if new user");

        if (requestRepository.countRequestsByChatId(chatId) == 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
