package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
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
            logger.info("Processing update: {}", update);
            Message message = update.message();
            Long chatId = message.chat().id();
            try {
                if (update.message() == null) {
                    createButtonClick(update);
                } else {
                    sendStartMessage(chatId);
                    createButtonClick(update);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void sendStartMessage(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Cat's shelter").callbackData("Cat's shelter"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("Dog's shelter").callbackData("Dog's shelter"));
        SendMessage sendMessage = new SendMessage(chatId, "Please, pick the shelter");
        sendMessage.replyMarkup(inlineKeyboardMarkup);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }
    private void createButtonClick(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        String data = callbackQuery.data();
        Long chatId = update.message().chat().id();
        switch (data) {
            case "Cat's shelter":
                getCatShelterClick(chatId);
                break;
            case "Dog's shelter":
                getDogShelterClick(chatId);
                break;
        }
    }

    private void getCatShelterClick(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "cat's menu");
        telegramBot.execute(sendMessage);
    }

    private void getDogShelterClick(Long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "dog's menu");
        telegramBot.execute(sendMessage);
    }
}

