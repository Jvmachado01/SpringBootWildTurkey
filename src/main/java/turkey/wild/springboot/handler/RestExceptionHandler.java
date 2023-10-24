package turkey.wild.springboot.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import turkey.wild.springboot.exception.BadRequestException;
import turkey.wild.springboot.exception.BadRequestExceptionDetails;

import java.time.LocalDateTime;

// Indicate to the controller that this class contains information that can be used by it.
@ControllerAdvice
// We can have several types of exceptions within this class. So the name RestExceptionHandler.
public class RestExceptionHandler {

    // We are informing the controller in case there is "Bad Request"
    // the method "handlerBadRequestException" will be called.
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .title("Bad Reques Exception. Check the Documentation")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);

    }
}
