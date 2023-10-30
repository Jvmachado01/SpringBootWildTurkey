package turkey.wild.springboot.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.requests.FilmPutRequestBody;
import turkey.wild.springboot.service.FilmService;
import turkey.wild.springboot.util.FilmCreator;
import turkey.wild.springboot.util.FilmPostRequestBodyCreator;
import turkey.wild.springboot.util.FilmPutRequestBodyCreator;

import java.util.Collections;
import java.util.List;

// SpringExtension integrates the Spring TestContext Framework
// into JUnit 5's Jupiter programming model.
@ExtendWith(SpringExtension.class)
class FilmControllerTest {
    @InjectMocks // When I want to test the class itself
    private FilmController filmController;
    @Mock // Uses when I want to test all classes within FilmController
    private FilmService filmServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Film> filmPage = new PageImpl<>(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(filmPage);

        BDDMockito.when(filmServiceMock.listAllNonPageable())
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.when(filmServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(FilmCreator.createValidFilm()));

        BDDMockito.when(filmServiceMock.save(ArgumentMatchers.any(FilmPostRequestBody.class)))
                .thenReturn(FilmCreator.createValidFilm());

        BDDMockito.doNothing().when(filmServiceMock).replace(ArgumentMatchers.any(FilmPutRequestBody.class));

        BDDMockito.doNothing().when(filmServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("list returns list of film inside page object when sucessful")
    void list_ReturnsListOfFilmsInsidePageObject_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        Page<Film> filmPage = filmController.list(null).getBody();

        Assertions.assertThat(filmPage).isNotNull();
        Assertions.assertThat(filmPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(filmPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of film inside when sucessful")
    void listAll_ReturnsListOfFilms_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        List<Film> films = filmController.listAll().getBody();

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns film when sucessful")
    void findById_ReturnsFilms_WhenSucessful() {
        Long expectedId = FilmCreator.createValidFilm().getId();
        Film film = filmController.findById(1).getBody();

        Assertions.assertThat(film).isNotNull();
        Assertions.assertThat(film.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of film when sucessful")
    void findByName_ReturnsListOfFilms_WhenSucessful() {
        String expectedName = FilmCreator.createValidFilm().getName();
        List<Film> films = filmController.findByName("film").getBody();

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of film when film is not found")
    void findByName_ReturnsEmptyListOfFilms_WhenFilmIsNotFound() {
        BDDMockito.when(filmServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Film> films = filmController.findByName("film").getBody();

        Assertions.assertThat(films).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns film when sucessful")
    void save_ReturnsFilms_WhenSucessful() {
        Long expectedId = FilmCreator.createValidFilm().getId();
        Film film = filmController.save(FilmPostRequestBodyCreator.createFilmPostRequestBody()).getBody();

        Assertions.assertThat(film).isNotNull().isEqualTo(FilmCreator.createValidFilm());
    }

    @Test
    @DisplayName("replace updates film when sucessful")
    void replace_UpdatesFilm_WhenSucessful() {
        Assertions.assertThatCode(() -> filmController.delete(1)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = filmController.delete(1);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes film when sucessful")
    void delete_RemovesFilm_WhenSucessful() {
        Assertions.assertThatCode(() -> filmController.replace(FilmPutRequestBodyCreator.createFilmPuttRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = filmController.replace(FilmPutRequestBodyCreator.createFilmPuttRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}