package pro.sky.animalizer.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.animalizer.Buttons.CatOrDog;


import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);




    private final TelegramBot telegramBot;

    private final CatOrDog catOrDog;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, CatOrDog catOrDog) {
        this.telegramBot = telegramBot;
        this.catOrDog = catOrDog;
    }



    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            long chatId = update.message().chat().id();
            String name = update.message().chat().firstName();

            if (update.message().text() != null && update.message().text().equals("/start")){

                // приветственное сообщение
                startMessage(chatId,name);

            }
            if(update.message().text().equals("/help")){
                //  выбор приюта для кошек или собак
                sendMessageWithButtons(chatId);


            }


        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void startMessage(long chatId, String name){

        String answer = "Hi, " + name + ", nice to meet you!";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String answer) {
        telegramBot.execute(new SendMessage(String.valueOf(chatId), answer));

    }

    private void sendMessageWithButtons(long chatId) {



    }











}








