package turkey.wild.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turkey.wild.springboot.domain.Film;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
