package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.UserRepository;

import java.util.List;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    public User findUserById(long id) {
        logger.info("start method findUserById");
        return userRepository.getById(id);
    }

    public List<User> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }



    //выполнить проверку на юзера перед изменением
    public User editUser(User user) {
        logger.info("start method editUser");
        return userRepository.save(user);

    }

    public void deleteUserById(long id) {
        logger.info("start method deleteUserById");
        userRepository.deleteById(id);
    }


}
