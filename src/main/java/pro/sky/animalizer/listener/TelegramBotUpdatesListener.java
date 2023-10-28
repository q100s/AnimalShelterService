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
        try {
            updates.forEach(update -> {
                Message message = update.message();
                String text;
                Long chatId;
                logger.info("Processing update: {}", update);
                if (update.message() != null) {
                    text = message.text();
                    chatId = message.chat().id();
                } else if (update.callbackQuery() != null) {
                    text = update.callbackQuery().data();
                    chatId = update.callbackQuery().message().chat().id();
                } else {
                    return;
                }
                if ("/start".equalsIgnoreCase(text)) {
                    greetingNewUser(chatId);
                }
                createButtonClick(update, chatId);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void greetingNewUser(Long chatId) {
        SendMessage sendMessage =
                new SendMessage(chatId, "Hello! Pick, please, the shelter!");
        sendMessage.replyMarkup(createButtonsShelterTypeSelect());
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public InlineKeyboardMarkup createButtonsShelterTypeSelect() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("cat's shelter").callbackData("cat's shelter"));
        inlineKeyboardMarkup.addRow(new InlineKeyboardButton("dog's shelter").callbackData("dog's shelter"));
        return inlineKeyboardMarkup;
    }

    public void createButtonClick(Update update, Long chatId) {
        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            String data = callbackQuery.data();
            switch (data) {
                case "cat's shelter":
                    telegramBot.execute(new SendMessage(chatId, "cats"));
                    break;
                case "dog's shelter":
                    telegramBot.execute(new SendMessage(chatId, "dogs"));
                    break;
            }
        }
    }
}

