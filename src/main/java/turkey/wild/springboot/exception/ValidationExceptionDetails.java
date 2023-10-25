package turkey.wild.springboot.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final String fields; // final for the Lombok include in the builder and get
    private final String fieldsMessage;
}
