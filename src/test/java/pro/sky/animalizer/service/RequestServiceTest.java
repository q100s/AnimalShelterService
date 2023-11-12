package pro.sky.animalizer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.animalizer.model.Request;

@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;
    Request request = new Request();
    Long requestsId;
    Long requestChatId;
    @BeforeEach
    void createNewRequest() {
        requestChatId = 111L;
        String requestText = "Test - Request with chatId =  111";
        request.setChatId(requestChatId);
        request.setRequestText(requestText);

    }
    @AfterEach
    void deleteNewRequest() {
        requestsId = request.getId();
        requestService.deleteRequest(requestsId);
    }
    @Test
    public void testSaveRequest() {
        Request savedRequest = requestService.saveRequest(request);
        Assertions.assertThat(savedRequest).isNotNull();
        Assertions.assertThat(request.getRequestText()).isEqualTo(savedRequest.getRequestText());
    }

    @Test
    public void testGetAllRequests() {
        requestService.saveRequest(request);
        Assertions.assertThat(requestService.getAllRequests()).isNotNull();
        Assertions.assertThat(requestService.getAllRequests().size()).isEqualTo(1);
    }

    @Test
    public void testGetAllRequestsByChatId() {
        requestService.saveRequest(request);
        Assertions.assertThat(requestService.getAllRequestsByChatId(requestChatId)).isNotNull();
    }
}
