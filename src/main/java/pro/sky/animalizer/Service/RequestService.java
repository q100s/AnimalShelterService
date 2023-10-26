package pro.sky.animalizer.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalizer.model.Request;
import pro.sky.animalizer.repositories.RequestRepository;

import java.util.Collection;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public Request saveRequest(Request request) {
        logger.info("Was invoked method for save request");
        return requestRepository.save(request);
    }

    public void deleteRequest(Long id) {
        logger.info("Was invoked method for delete request by id" + id);
        requestRepository.deleteById(id);
    }

    public Request editRequest(Request request) {
        logger.info("Was invoked method for edit request");
        return requestRepository.save(request);
    }

    public Collection<Request> getAllRequests() {
        logger.info("Was invoked method for find all requests");
        return requestRepository.findAll();
    }

    public Collection<Request> getAllRequestsByChatId(Long chatId) {
        logger.info("Was invoked method for find all requests by tg_chat_id");
        return requestRepository.findAllRequestsByChatId(chatId);
    }

    public boolean checkIfNewUser(Long chatId) {
        logger.info("Was invoked method for check if new user");

        if(requestRepository.countRequestsByChatId(chatId) == 0) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
