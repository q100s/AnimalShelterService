package pro.sky.animalizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShelterNotFoundException extends RuntimeException {
    public ShelterNotFoundException() {
        super();
    }

    public ShelterNotFoundException(String message) {
        super(message);
    }

    public ShelterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShelterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ShelterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
