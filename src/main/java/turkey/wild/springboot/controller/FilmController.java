package turkey.wild.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.requests.FilmPutRequestBody;
import turkey.wild.springboot.service.FilmService;
import turkey.wild.springboot.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("films")
@Log4j2
@RequiredArgsConstructor
public class FilmController {
    private final DateUtil dateUtil;
    private final FilmService filmService; // final for injection of dependencies

    @GetMapping
    public ResponseEntity<List<Film>> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
//        return new ResponseEntity<>(filmService.listAll(), HttpStatus.OK);
        return ResponseEntity.ok(filmService.listAll());

    }

    @GetMapping(path = "/{id}") // url param
    public ResponseEntity<Film> findById(@PathVariable long id) {
        return ResponseEntity.ok(filmService.findByIdOrThrowBadRequestException(id));
    }

    // http://localhost:8080/films/find?name=Casino  <= exemplo.
    @GetMapping(path = "/find") // request param
    public ResponseEntity<List<Film>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(filmService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Film> save(@RequestBody FilmPostRequestBody filmPostRequestBody) {
        return new ResponseEntity<>(filmService.save(filmPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody FilmPutRequestBody filmPutRequestBody) {
        filmService.replace(filmPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
