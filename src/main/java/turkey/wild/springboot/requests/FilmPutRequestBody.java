package turkey.wild.springboot.requests;

import lombok.Data;

@Data
// DTO (Data transfer object) class
public class FilmPutRequestBody {
    private Long id;
    private String name;

}

