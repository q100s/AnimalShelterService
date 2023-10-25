package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.Request;
import pro.sky.animalizer.repositories.RequestRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.time.LocalDateTime.now;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {


    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private RequestRepository requestRepository;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            // Process updates
            if (update.message().text() != null) {
                testRequests();
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void testRequests() {
        System.out.println("Hello test-request");

        if(requestRepository.countRequestsByChatId(111L) != 0) {
            System.out.println("Обращение от  usera 111L уже было");
        } else {
            System.out.println("Обращение от  usera 111L НЕ было");
        }

        Request request = new Request();
        request.setChatId(111L);
        request.setRequestTime(now());
        request.setRequestText("Test 111");

        System.out.println("Save request user 111L");
        requestRepository.save(request);
        if(requestRepository.countRequestsByChatId(111L) == 0) {
            System.out.println("Обращений от chatID = 111L еще НЕ было");
        } else {
            System.out.println("Обращение от chatID = 111L уже было " + requestRepository.countRequestsByChatId(111L) + " раз");
        }

        System.out.println("Обращение от chatID = 333L было " + requestRepository.countRequestsByChatId(333L) + " раз");


    } // end test if request from new chat_id
}