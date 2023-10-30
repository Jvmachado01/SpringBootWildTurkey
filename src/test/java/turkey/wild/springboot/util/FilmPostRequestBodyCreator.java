package turkey.wild.springboot.util;

import turkey.wild.springboot.requests.FilmPostRequestBody;

public class FilmPostRequestBodyCreator {
    public static FilmPostRequestBody createFilmPostRequestBody() {
        return FilmPostRequestBody.builder()
                .name(FilmCreator.createFilmToBeSaved().getName())
                .build();
    }
}
