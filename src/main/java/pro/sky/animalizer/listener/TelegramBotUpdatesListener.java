package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.Service.RequestService;
import pro.sky.animalizer.model.Request;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.time.LocalDateTime.now;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {


    @Autowired
    private TelegramBot telegramBot;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            // Process updates
            if (update.message().text() != null) {
                System.out.println("update = " + update.message().text());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}