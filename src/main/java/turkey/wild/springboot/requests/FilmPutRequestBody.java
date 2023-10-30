package turkey.wild.springboot.requests;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
// DTO (Data transfer object) class
public class FilmPutRequestBody {
    private Long id;
    private String name;

}

