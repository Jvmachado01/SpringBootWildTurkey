package turkey.wild.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.exception.BadRequestException;
import turkey.wild.springboot.mapper.FilmMapper;
import turkey.wild.springboot.repository.FilmRepository;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.requests.FilmPutRequestBody;

import java.util.List;

@Service // to be a SpringBin
@RequiredArgsConstructor // for Spring make dependencies injection
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> listAll() {
        return filmRepository.findAll();
    }

    public List<Film> findByName(String name) {
        return filmRepository.findByName(name);
    }

    public Film findByIdOrThrowBadRequestException(long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Film not found"));

    }

    public Film save(FilmPostRequestBody filmPostRequestBody) {
        return filmRepository.save(FilmMapper.INSTANCE.toFilm(filmPostRequestBody));
    }

    public void delete(long id) {
        filmRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(FilmPutRequestBody filmPutRequestBody) {
        Film savedFilm = findByIdOrThrowBadRequestException(filmPutRequestBody.getId());
        Film film = FilmMapper.INSTANCE.toFilm(filmPutRequestBody);
        film.setId(savedFilm.getId());
        filmRepository.save(film);
    }
}
