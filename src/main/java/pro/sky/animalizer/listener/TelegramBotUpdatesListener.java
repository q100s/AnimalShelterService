package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            Message message = update.message();
            Long chatId = update.message().chat().id();
            logger.info("Processing update: {}", update);
            if (update.message().newChatMembers() != null) {
                telegramBot.execute(new SendMessage(chatId, "Hello! I'm an animal shelter's bot."));
                sendMenuWithShelterPicking(chatId, telegramBot);
            } else {
                sendMenuWithShelterPicking(chatId, telegramBot);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private static void sendMenuWithShelterPicking(Long chatId, TelegramBot telegramBot) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Cat's shelter").callbackData("Cat's shelter"),
                        new InlineKeyboardButton("Dog's shelter").callbackData("Dog's shelter"),
                });
        telegramBot.execute(new SendMessage(chatId, "Please, pick the shelter").replyMarkup(inlineKeyboard));
    }

    private static void sendMenuWithInfoAndReport(Long chatId) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
    }
}