package turkey.wild.springboot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.exception.BadRequestException;
import turkey.wild.springboot.mapper.FilmMapper;
import turkey.wild.springboot.repository.FilmRepository;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.requests.FilmPutRequestBody;

import java.util.List;

@Service // to be a Spring bean
@RequiredArgsConstructor // for Spring make dependencies injection
public class FilmService {

    private final FilmRepository filmRepository;

    // Page generic for pagination
    public Page<Film> listAll(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    public List<Film> listAllNonPageable() {
        return filmRepository.findAll();
    }

    public List<Film> findByName(String name) {
        return filmRepository.findByName(name);
    }

    public Film findByIdOrThrowBadRequestException(long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Film not found"));

    }
    

    @Transactional // For rollback - @Transactional(rollbackOn = Exception.class) for checked.
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
