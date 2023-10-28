package turkey.wild.springboot.repository;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import turkey.wild.springboot.domain.Film;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// By default, tests annotated with @DataJpaTest are transactional
// and roll back at the end of each test. They also use an embedded in-memory database
// (replacing any explicit or usually auto-configured DataSource).
@DataJpaTest
@DisplayName("Tests for Film Repository")
@Log4j2
class FilmRepositoryTest {

    /*
     Spring @Autowired annotation is used for automatic dependency injection.
     Spring framework is built on dependency injection and we inject
     the class dependencies through spring bean configuration file.

     Warning: It is not recommended to use. In tests everything is fine.
     But outside of tests, use constructor!
    */
    @Autowired
    private FilmRepository filmRepository;

    @Test
    @DisplayName("Save persists film when sucessful")
    void save_PersistsFilm_WhenSucessful() {
        Film filmToBeSaved = createFilm();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);

        Assertions.assertThat(filmSaved).isNotNull();
        Assertions.assertThat(filmSaved.getId()).isNotNull();
        Assertions.assertThat(filmSaved.getName()).isEqualTo(filmToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates film when sucessful")
    void save_UpdatesFilm_WhenSucessful() {
        Film filmToBeSaved = createFilm();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);
        filmSaved.setName("Casino 2");
        Film filmUpdated = this.filmRepository.save(filmSaved);

        Assertions.assertThat(filmUpdated).isNotNull();
        Assertions.assertThat(filmUpdated.getId()).isNotNull();
        Assertions.assertThat(filmUpdated.getName()).isEqualTo(filmSaved.getName());
    }

    @Test
    @DisplayName("Delete removes film when sucessful")
    void delete_RemovesFilm_WhenSucessful() {
        Film filmToBeSaved = createFilm();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);
        this.filmRepository.delete(filmSaved);
        Optional<Film> filmOptional = this.filmRepository.findById(filmSaved.getId());

        Assertions.assertThat(filmOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of film when sucessful")
    void findByName_ReturnsListOfFilm_WhenSucessful() {
        Film filmToBeSaved = createFilm();
        Film filmSaved = this.filmRepository.save(filmToBeSaved);
        String name = filmSaved.getName();
        List<Film> films = this.filmRepository.findByName(name);

        Assertions.assertThat(films).isNotEmpty().contains(filmSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list of film when no film is found")
    void findByName_ReturnsEmptyListFilm_WhenFilmIsNotFound() {
        List<Film> films = this.filmRepository.findByName("No exist");

        Assertions.assertThat(films).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Film film = new Film();
//        Assertions.assertThatThrownBy(() -> this.filmRepository.save(film))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.filmRepository.save(film))
                .withMessageContaining("The film name cannot be empty");
    }

    private Film createFilm() {
        return Film.builder().name("Casino").build();
    }

}