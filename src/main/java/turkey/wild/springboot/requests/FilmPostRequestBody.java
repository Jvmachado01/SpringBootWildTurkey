package turkey.wild.springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;


@Data
// DTO (Data transfer object) class
public class FilmPostRequestBody {
    @NotEmpty(message = "The film name cannot be empty")
    private String name;
}

