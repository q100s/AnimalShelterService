package pro.sky.animalizer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.animalizer.model.Request;
import static java.time.LocalDateTime.now;

@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;


    // test of saveRequest  in DB method
    @Test
    public void testSaveRequest() {
        Request request = new Request();
        Long requestChatId = 111L;
        String requestText = "Test - Request with chatId =  111";
        request.setChatId(requestChatId);
        request.setRequestTime(now());
        request.setRequestText(requestText);
        Request savedRequest = requestService.saveRequest(request);
        Assertions.assertThat(savedRequest).isNotNull();
        Assertions.assertThat(request.getRequestText()).isEqualTo(savedRequest.getRequestText());

    }

    // test of getAllRequests method
    @Test
    public void testGetAllRequests() {
        Request request = new Request();
        Long requestChatId = 111L;
        String requestText = "Test - Request with chatId =  111";
        request.setChatId(requestChatId);
        request.setRequestTime(now());
        request.setRequestText(requestText);
        requestService.saveRequest(request);

        Assertions.assertThat(requestService.getAllRequests()).isNotNull();
    }

    // test of getAllRequestsByChatId method
    @Test
    public void testGetAllRequestsByChatId() {
        Request request = new Request();
        Long requestChatId = 111L;
        String requestText = "Test - Request with chatId =  111";
        request.setChatId(requestChatId);
        request.setRequestTime(now());
        request.setRequestText(requestText);
        requestService.saveRequest(request);

        Assertions.assertThat(requestService.getAllRequestsByChatId(requestChatId)).isNotNull();
    }

    // test of checkIfNewUser method
    @Test
    public void testCheckIfNewUser() {
        Request request = new Request();
        Long requestChatId = 111L;
        String requestText = "Test - Request with chatId =  111";
        request.setChatId(requestChatId);
        request.setRequestTime(now());
        request.setRequestText(requestText);
        requestService.saveRequest(request);

        Assertions.assertThat(requestService.checkIfNewUser(requestChatId)).isEqualTo(false);
        requestChatId = -1L;
        Assertions.assertThat(requestService.checkIfNewUser(requestChatId)).isEqualTo(true);
    }


}
