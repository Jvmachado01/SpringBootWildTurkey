package turkey.wild.springboot.util;

import turkey.wild.springboot.domain.Film;

public class FilmCreator {

    public static Film createFilmToBeSaved() {
        return Film.builder().name("Casino").build();
    }

    public static Film createValidFilm() {
        return Film.builder().name("Casino").id(1L).build();
    }

    public static Film createValidUpdatedFilm() {
        return Film.builder().name("Casino 2").id(1L).build();
    }
}
