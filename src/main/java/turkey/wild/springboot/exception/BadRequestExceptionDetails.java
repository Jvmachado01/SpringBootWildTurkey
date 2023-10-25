package turkey.wild.springboot.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder // also works with fields from superclasses.
public class BadRequestExceptionDetails extends ExceptionDetails {

}
