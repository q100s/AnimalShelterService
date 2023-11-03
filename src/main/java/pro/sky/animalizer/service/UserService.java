package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.Shelter_user;
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

    public Shelter_user createUser(Shelter_user shelterUser) {
        logger.info("start method createUser");
        logger.info("User created");
        return userRepository.save(shelterUser);
    }

    public Shelter_user findUserById(long id) {
        logger.info("start method findUserById");
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<Shelter_user> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }



    //выполняется проверка на юзера перед изменением. Если такой есть- меняется
    //если нет- ошибка
    public Shelter_user editUser(long id,Shelter_user shelterUser) {
        logger.info("start method editUser");
        Shelter_user userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(shelterUser.getFull_name()).ifPresent(userCheck::setFull_name);
        Optional.ofNullable(shelterUser.getPhone_number()).ifPresent(userCheck::setPhone_number);
        return userRepository.save(shelterUser);

    }

    public void deleteUserById(long id) {
        logger.info("start method deleteUserById");
        Shelter_user userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }


}
