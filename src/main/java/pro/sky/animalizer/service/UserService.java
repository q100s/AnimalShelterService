package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис с бизнес-логикой по работе с пользователями.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        logger.info("start method createUser");
        logger.info("User created");
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        logger.info("start method findUserById");
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByTelegramId(Long telegramId) {
        logger.info("start method findUserByTelegramId");
        return userRepository.findByTelegramId(telegramId);
    }

    public List<User> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }

    /**
     * Выполняется проверка на наличие юзера в БД перед изменением. Если такой есть- меняется
     *если нет- ошибка
     */
    public User editUser(Long id, User user) {
        logger.info("start method editUser");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getFullName()).ifPresent(userCheck::setFullName);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(userCheck::setPhoneNumber);
        Optional.ofNullable(user.getCarNumber()).ifPresent(userCheck::setCarNumber);
        Optional.ofNullable(user.getTelegramId()).ifPresent(userCheck::setTelegramId);
        Optional.ofNullable(user.getTelegramNick()).ifPresent(userCheck::setTelegramNick);
        return userRepository.save(user);

    }

    public void deleteUserById(Long id) {
        logger.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }
}
