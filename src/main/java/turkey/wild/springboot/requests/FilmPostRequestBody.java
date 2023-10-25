package turkey.wild.springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
// DTO (Data transfer object) class
public class FilmPostRequestBody {
    @NotEmpty(message = "The film name cannot be empty")
    @NotNull(message = "The film name cannot be null")
    private String name;

}

