package turkey.wild.springboot.util;

import turkey.wild.springboot.requests.FilmPutRequestBody;

public class FilmPutRequestBodyCreator {
    public static FilmPutRequestBody createFilmPuttRequestBody() {
        return FilmPutRequestBody.builder()
                .id(FilmCreator.createValidUpdatedFilm().getId())
                .name(FilmCreator.createValidUpdatedFilm().getName())
                .build();
    }
}
