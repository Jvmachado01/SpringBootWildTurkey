package turkey.wild.springboot.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import turkey.wild.springboot.domain.Film;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Film> entity = new RestTemplate().getForEntity("http://localhost:8080/films/{id}", Film.class, 3);
        log.info(entity);

        // Take only the object id 3 Film
        Film object = new RestTemplate().getForObject("http://localhost:8080/films/{id}", Film.class, 3);
        log.info(object);
    }
}
