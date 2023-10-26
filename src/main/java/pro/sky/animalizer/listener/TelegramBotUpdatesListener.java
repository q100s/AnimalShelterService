package pro.sky.animalizer.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.Service.RequestService;
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
    private RequestService requestService;


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

        if(requestService.checkIfNewUser(111L)) {
            System.out.println("Обращение от  usera 111L НЕ было");
        } else {
            System.out.println("Обращение от  usera 111L уже было");
        }

        Request request = new Request();
        request.setChatId(111L);
        request.setRequestTime(now());
        request.setRequestText("Test 111");

        System.out.println("Save request user 111L");
        requestService.saveRequest(request);
        if(requestService.checkIfNewUser(111L)) {
            System.out.println("Обращение от  usera 111L НЕ было");
        } else {
            System.out.println("Обращение от  usera 111L уже было");
        }

        if(requestService.checkIfNewUser(333L)) {
            System.out.println("Обращение от  usera 333L НЕ было");
        } else {
            System.out.println("Обращение от  usera 333L уже было");
        }

        System.out.println(requestService.getAllRequests());
        System.out.println(requestService.getAllRequestsByChatId(111L));

    } // end test if request from new chat_id
}