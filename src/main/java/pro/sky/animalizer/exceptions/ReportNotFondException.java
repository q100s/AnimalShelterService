package pro.sky.animalizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportNotFondException extends RuntimeException{
    public ReportNotFondException() {
    }

    public ReportNotFondException(String message) {
        super(message);
    }

    public ReportNotFondException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportNotFondException(Throwable cause) {
        super(cause);
    }

    public ReportNotFondException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
