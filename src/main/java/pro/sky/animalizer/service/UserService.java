package pro.sky.animalizer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.repositories.ReportRepository;
import pro.sky.animalizer.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс-сервис с бизнес-логикой по работе с пользователями.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ReportService reportService;



    public UserService(UserRepository userRepository, ReportService reportService) {
        this.userRepository = userRepository;

        this.reportService = reportService ;
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

    public User findUserByTelegramId(Long telegramId) {
        logger.info("start method findUserByTelegramId");
        return userRepository.findByTelegramId(telegramId);
    }

    public List<User> getAllUsers() {
        logger.info("start method getAllUserFromUserRepository");
        return userRepository.findAll();
    }

    public User editUser(Long id, User user) {
        logger.info("start method editUser");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getFullName()).ifPresent(userCheck::setFullName);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(userCheck::setPhoneNumber);
        Optional.ofNullable(user.getTelegramId()).ifPresent(userCheck::setTelegramId);
        Optional.ofNullable(user.getTelegramNick()).ifPresent(userCheck::setTelegramNick);
        Optional.ofNullable(user.getUserType()).ifPresent(userCheck::setUserType);
        return userRepository.save(userCheck);
    }

    public void deleteUserById(Long id) {
        logger.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }

    public Collection<Report>getUsersWhoDoNotSendReportMoreThen2Days(LocalDate localDate){
        return reportService.getAllUsersWhoNotSendReport(localDate);

    }


}