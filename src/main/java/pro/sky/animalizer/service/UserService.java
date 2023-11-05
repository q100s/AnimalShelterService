package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User shelterUser) {
        logger.info("start method createUser");
        logger.info("User created");
        return userRepository.save(shelterUser);
    }

    public User findUserById(long id) {
        logger.info("start method findUserById");
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
    public User findUserByTelegramId(long telegramId) {
        logger.info("start method findUserByTelegramId");
        return userRepository.findUserByTelegramId(telegramId);
    }

    public List<User> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }

    //выполняется проверка на юзера перед изменением. Если такой есть- меняется
    //если нет- ошибка
    public User editUser(long id, User shelterUser) {
        logger.info("start method editUser");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(shelterUser.getFullName()).ifPresent(userCheck::setFullName);
        Optional.ofNullable(shelterUser.getPhoneNumber()).ifPresent(userCheck::setPhoneNumber);
        return userRepository.save(shelterUser);

    }

    public void deleteUserById(long id) {
        logger.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }
}
