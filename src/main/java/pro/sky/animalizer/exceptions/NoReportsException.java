package pro.sky.animalizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoReportsException extends RuntimeException {
    public NoReportsException() {
    }

    public NoReportsException(String message) {
        super(message);
    }

    public NoReportsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoReportsException(Throwable cause) {
        super(cause);
    }

    public NoReportsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
