package pro.sky.animalizer.Buttons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import pro.sky.animalizer.TelegramBotUpdatesListener.TelegramBotUpdatesListener;

import java.util.ArrayList;
import java.util.List;
@Configuration
public class CatOrDog {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    public SendMessage catDogInLineKeyBoard(long chatId) {
        // message
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("выберете приют для кошек или собак");

        //объект клавиатуры
        InlineKeyboardMarkup markupLine = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

        //button1
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("for cats");
        logger.info("кнопка с котами создана");

        // вернет слово cats
        inlineKeyboardButton1.setCallbackData("cats");


        // вторая кнопка
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("for dogs");
        inlineKeyboardButton2.setCallbackData("dogs");
        logger.info("кнопка с собаками создана");

        rowsInline.add(rowInline1);


        rowInline1.add(inlineKeyboardButton1);
        rowInline1.add(inlineKeyboardButton2);
        logger.info("кнопки добавлены в линию");


        markupLine.setKeyboard(rowsInline);
        message.setReplyMarkup(markupLine);


        return message;
    }
}