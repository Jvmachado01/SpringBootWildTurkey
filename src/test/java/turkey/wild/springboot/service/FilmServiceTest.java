package turkey.wild.springboot.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.exception.BadRequestException;
import turkey.wild.springboot.repository.FilmRepository;
import turkey.wild.springboot.util.FilmCreator;
import turkey.wild.springboot.util.FilmPostRequestBodyCreator;
import turkey.wild.springboot.util.FilmPutRequestBodyCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class FilmServiceTest {
    @InjectMocks // When I want to test the class itself
    private FilmService filmService;
    @Mock // Uses when I want to test all classes within FilmController
    private FilmRepository filmRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Film> filmPage = new PageImpl<>(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(filmPage);

        BDDMockito.when(filmRepositoryMock.findAll())
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmRepositoryMock.save(ArgumentMatchers.any(Film.class)))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.doNothing().when(filmRepositoryMock).delete(ArgumentMatchers.any(Film.class));

    }

    @Test
    @DisplayName("listAll returns list of film inside page object when sucessful")
    void listAll_ReturnsListOfFilmsInsidePageObject_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        Page<Film> filmPage = filmService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(filmPage).isNotNull();
        Assertions.assertThat(filmPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(filmPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of film inside when sucessful")
    void listAllNonPageable_ReturnsListOfFilms_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        List<Film> films = filmService.listAllNonPageable();

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns film when sucessful")
    void findByIdOrThrowBadRequestException_ReturnsFilms_WhenSucessful() {
        Long expectedId = FilmCreator.createValidFilm().getId();
        Film film = filmService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(film).isNotNull();
        Assertions.assertThat(film.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException  when film is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenFilmIsNotFound() {
        BDDMockito.when(filmRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> filmService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("findByName returns a list of film when sucessful")
    void findByName_ReturnsListOfFilms_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        List<Film> films = filmService.findByName("film");

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of film when film is not found")
    void findByName_ReturnsEmptyListOfFilms_WhenFilmIsNotFound() {
        BDDMockito.when(filmRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Film> films = filmService.findByName("film");

        Assertions.assertThat(films).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns film when sucessful")
    void save_ReturnsFilms_WhenSucessful() {
        Long expectedId = FilmCreator.createValidFilm().getId();
        Film film = filmService.save(FilmPostRequestBodyCreator.createFilmPostRequestBody());

        Assertions.assertThat(film).isNotNull().isEqualTo(FilmCreator.createValidFilm());
    }

    @Test
    @DisplayName("replace updates film when sucessful")
    void replace_UpdatesFilm_WhenSucessful() {
        Assertions.assertThatCode(() -> filmService.delete(1)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes film when sucessful")
    void delete_RemovesFilm_WhenSucessful() {
        Assertions.assertThatCode(() -> filmService.replace(FilmPutRequestBodyCreator.createFilmPuttRequestBody()))
                .doesNotThrowAnyException();
    }

}