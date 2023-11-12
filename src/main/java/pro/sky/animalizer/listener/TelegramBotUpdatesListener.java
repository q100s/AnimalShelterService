package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.ShelterService;
import pro.sky.animalizer.service.UserRequestService;
import pro.sky.animalizer.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, уведомляемый о событии. <br>
 * Он должен быть зарегистрирован источником событий
 * и реализовывать методы для получения и обработки уведомлений.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Pattern pattern = Pattern.compile("(^[А-я]+)\\s+([А-я]+)\\s+(\\d{11}$)");
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final UserRequestService userRequestService;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      UserService userService,
                                      UserRequestService userRequestService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.userRequestService = userRequestService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                if (update.message() != null) {
                    sendStartMessage(update);
                }
                userRequestService.createButtonClick(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMessage(Update update) {
        Message message = update.message();
        Long chatId = message.from().id();
        String text = message.text();
        String firstName = update.message().from().firstName();
        String userName = update.message().from().username();
        long telegramId = update.message().from().id();
        Matcher matcher = pattern.matcher(text);
        if ("/start".equalsIgnoreCase(text)) {
            User user = userService.findUserByTelegramId(telegramId);
            if (user == null) {
                telegramBot.execute(
                        new SendMessage(chatId, "Приветсвую тебя меню приюта для животных, " + firstName)
                );
                User newUser = new User(telegramId, userName);
                userService.createUser(newUser);
                userRequestService.getMenuWithShelterPicking(chatId);
            } else {
                telegramBot.execute(new SendMessage(chatId, "Рад видеть тебя снова, " + firstName));
                userRequestService.getMenuWithShelterPicking(chatId);
            }
        } else if (matcher.find()) {
            String fullName = matcher.group(1) + " " + matcher.group(2);
            String phoneNumber = matcher.group(3);
            userRequestService.updateUser(update, fullName, phoneNumber);
        }
    }
}