package pro.sky.animalizer.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.model.UserType;
import pro.sky.animalizer.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Класс-сервис с бизнес-логикой по работе с пользователями.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PetService petService;
    private final TelegramBot telegramBot;

    public UserService(UserRepository userRepository,
                       PetService petService,
                       TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.petService = petService;
        this.telegramBot = telegramBot;
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
        User editedUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        Optional.ofNullable(user.getFullName()).ifPresent(editedUser::setFullName);
        Optional.ofNullable(user.getPhoneNumber()).ifPresent(editedUser::setPhoneNumber);
        editedUser.setUserType(user.getUserType());
        return userRepository.save(editedUser);
    }

    public void deleteUserById(Long id) {
        logger.info("start method deleteUserById");
        User userCheck = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(userCheck);
    }

    public User connectUserAndPet(Long userId, Long petId) {
        logger.info("start method approveAdopterAndSendCongratulation");
        User newAdopter = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Pet adoptedPet = petService.getPetById(petId);
        adoptedPet.setAdopter(newAdopter);
        newAdopter.setUserType(UserType.ADOPTER);
        newAdopter.setPet(adoptedPet);
        newAdopter.setEndingOfTrialPeriod(LocalDate.now().plusDays(30));
        userRepository.save(newAdopter);
        petService.createPet(adoptedPet);
        return newAdopter;
    }

    public User extendTrialPeriod(Long userId, int additionalDays) {
        User adopterForExtension = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Long telegramId = adopterForExtension.getTelegramId();
        adopterForExtension.setEndingOfTrialPeriod(LocalDate.now().plusDays(additionalDays));
        userRepository.save(adopterForExtension);
        telegramBot.execute(new SendMessage(telegramId, "Твой испытательный срок продлён на " + additionalDays + " дней"));
        return adopterForExtension;
    }

    public String approveAdopterAndSendCongratulation(Long userId) {
        logger.info("start method approveAdopterAndSendCongratulation");
        User approvedAdopter = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Pet approvedPet = petService.getPetByUserId(approvedAdopter.getId());
        Long telegramId = approvedAdopter.getTelegramId();
        userRepository.delete(approvedAdopter);
        petService.deletePet(approvedPet.getId());
        telegramBot.execute(new SendMessage(telegramId, "Поздравляю, ты прошёл испытательный срок!"));
        return "Пользователь с телеграм-id: " + telegramId + " прошёл испытательный срок";
    }

    public String rejectAdopter(Long userId) {
        logger.info("start method rejectAdopter");
        User rejectedAdopter = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Pet rejectedPet = petService.getPetByUserId(rejectedAdopter.getId());
        Long telegramId = rejectedAdopter.getTelegramId();
        userRepository.delete(rejectedAdopter);
        rejectedPet.setAdopter(null);
        petService.createPet(rejectedPet);
        telegramBot.execute(new SendMessage(telegramId, "Ты не прошел испрытательный срок, ожидай звонка от волонтера"));
        return "Пользователь с телеграм-id: " + telegramId + " не прошёл испытательный срок";
    }

    public List<User> findAllWithEndOfTrialPeriod() {
        return userRepository.findAll().stream()
                .filter(user -> user.getUserType().equals(UserType.VOLUNTEER))
                .filter(user -> user.getEndingOfTrialPeriod().equals(LocalDate.now()))
                .toList();
    }

    public String sendMessageAboutBadReport(Long userId) {
        User userForMessage = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String message = "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                "Пожалуйста, подойди ответственнее к этому занятию. " +
                "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного";
        telegramBot.execute(new SendMessage(userForMessage.getTelegramId(), message));
        return "пользователь с идентификатором " + userId + " получил предупреждение";
    }
}