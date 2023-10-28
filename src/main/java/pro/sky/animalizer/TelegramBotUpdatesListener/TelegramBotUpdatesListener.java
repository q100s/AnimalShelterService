package pro.sky.animalizer.TelegramBotUpdatesListener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
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
        logger.info("Bot is started");
        updates.forEach(update -> {

            Long chatId = null;
            String text = null;
            if (update.message() != null) {
                chatId = update.message().chat().id();
                text = update.message().text();
            }
            if (update.callbackQuery() != null) {
                text = update.callbackQuery().data();
                chatId = update.callbackQuery().message().chat().id();
            }
            if (update.message() != null && update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(chatId, "Hello! I'm an animal shelter's bot."));
                logger.info("The start message was been sent");
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("Cat's shelter").callbackData("Cat's shelter"),
                                new InlineKeyboardButton("Dog's shelter").callbackData("Dog's shelter")
                        });
                telegramBot.execute(new SendMessage(chatId, "Please, pick the shelter").replyMarkup(inlineKeyboard));
                logger.info("shelter selection buttons sent");
            }
            assert text != null;
            if (text.equalsIgnoreCase("Cat's shelter")) {
                logger.info("cat shelter button pressed");
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("but1").callbackData("but1"),
                                new InlineKeyboardButton("but2").callbackData("but2"),
                                new InlineKeyboardButton("but3").callbackData("but3"),
                                new InlineKeyboardButton("but4").callbackData("but4")
                        });
                telegramBot.execute(new SendMessage(chatId, "Please, pick button for cats shelter ").replyMarkup(inlineKeyboard));


            }
            if (text.equalsIgnoreCase("Dog's shelter")) {
                logger.info("dog shelter button pressed");
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton[]{
                                new InlineKeyboardButton("but1").callbackData("but1"),
                                new InlineKeyboardButton("but2").callbackData("but2"),
                                new InlineKeyboardButton("but3").callbackData("but3"),
                                new InlineKeyboardButton("but4").callbackData("but4")
                        });
                telegramBot.execute(new SendMessage(chatId, "Please, pick button for dogs shelter ").replyMarkup(inlineKeyboard));
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}







