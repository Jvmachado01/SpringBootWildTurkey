package turkey.wild.springboot.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.repository.FilmRepository;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.util.FilmCreator;
import turkey.wild.springboot.util.FilmPostRequestBodyCreator;
import turkey.wild.springboot.wrapper.PageableResponse;

import java.util.List;

// Will start the entire application
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// in-memory database
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    //    @LocalManagementPort // Take current prot
//    private int port;
    @Autowired
    private FilmRepository filmRepository;

    @Test
    @DisplayName("list returns list of film inside page object when sucessful")
    void list_ReturnsListOfFilmsInsidePageObject_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        String expectedName = savedFilm.getName();
        PageableResponse<Film> filmPage = testRestTemplate.exchange("/films",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Film>>() {
                }).getBody();

        Assertions.assertThat(filmPage).isNotNull();
        Assertions.assertThat(filmPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(filmPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of film inside when sucessful")
    void listAll_ReturnsListOfFilms_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        String expectedName = savedFilm.getName();
        List<Film> films = testRestTemplate.exchange("/films/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Film>>() {
                }).getBody();

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns film when sucessful")
    void findById_ReturnsFilms_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        Long expectedId = savedFilm.getId();
        Film film = testRestTemplate.getForObject("/films/{id}", Film.class, expectedId);

        Assertions.assertThat(film).isNotNull();
        Assertions.assertThat(film.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of film when sucessful")
    void findByName_ReturnsListOfFilms_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        String expectedName = savedFilm.getName();
        String url = String.format("/films/find?name=%s", expectedName);
        List<Film> films = testRestTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Film>>() {
                }).getBody();

        Assertions.assertThat(films).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(films.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of film when film is not found")
    void findByName_ReturnsEmptyListOfFilms_WhenFilmIsNotFound() {
        List<Film> films = testRestTemplate.exchange("/films/find?name=anyfilm",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Film>>() {
                }).getBody();

        Assertions.assertThat(films).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns film when sucessful")
    void save_ReturnsFilms_WhenSucessful() {
        FilmPostRequestBody filmPostRequestBody = FilmPostRequestBodyCreator.createFilmPostRequestBody();
        ResponseEntity<Film> filmResponseEntity = testRestTemplate.postForEntity("/films", filmPostRequestBody, Film.class);

        Assertions.assertThat(filmResponseEntity).isNotNull();
        Assertions.assertThat(filmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(filmResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(filmResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates film when sucessful")
    void replace_UpdatesFilm_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        savedFilm.setName("Casino 3");
        ResponseEntity<Void> filmResponseEntity = testRestTemplate.exchange("/films",
                HttpMethod.PUT, new HttpEntity<>(savedFilm), Void.class);

        Assertions.assertThat(filmResponseEntity).isNotNull();
        Assertions.assertThat(filmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes film when sucessful")
    void delete_RemovesFilm_WhenSucessful() {
        Film savedFilm = filmRepository.save(FilmCreator.createFilmToBeSaved());
        ResponseEntity<Void> filmResponseEntity = testRestTemplate.exchange("/films/{id}",
                HttpMethod.DELETE, null, Void.class, savedFilm.getId());

        Assertions.assertThat(filmResponseEntity).isNotNull();
        Assertions.assertThat(filmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
