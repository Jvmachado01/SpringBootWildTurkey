package turkey.wild.springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
// DTO (Data transfer object) class
public class FilmPostRequestBody {
    @NotEmpty(message = "The film name cannot be empty")
    private String name;
}

