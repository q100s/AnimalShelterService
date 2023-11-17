package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.User;
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
                    userRequestService.sendStartMessage(update);
                }
                userRequestService.createButtonClick(update);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}