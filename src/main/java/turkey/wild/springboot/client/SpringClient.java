package turkey.wild.springboot.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import turkey.wild.springboot.domain.Film;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Film> entity = new RestTemplate().getForEntity("http://localhost:8080/films/{id}", Film.class, 3);
        log.info(entity);

        // Take only the object id 3 Film
        Film object = new RestTemplate().getForObject("http://localhost:8080/films/{id}", Film.class, 3);
        log.info(object);

        // Let’s say I want to automatically map the data, but now the data is inside a list.
        // We can achieve the goal using the Array notation, where we will have an Array with type Film Object
        Film[] films = new RestTemplate().getForObject("http://localhost:8080/films/all", Film[].class);
        log.info(Arrays.toString(films));

        // to return a lista of the Film Object
        ResponseEntity<List<Film>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/films/all",
                HttpMethod.GET,
                null, // return null becouse it is Get
                // To make sure that every time you are making the exchange will be a Film list
                new ParameterizedTypeReference<List<Film>>() {
                });
        log.info(exchange.getBody()); // Return body of the ResponseEntity

//        Film rocky = Film.builder().name("Rocky").build();
//        Film rockySaved = new RestTemplate().postForObject("http://localhost:8080/films", rocky, Film.class);
//        log.info("Saved filme: {}", rockySaved);

        Film indiana = Film.builder().name("Indiana").build();
        ResponseEntity<Film> indianaSaved = new RestTemplate().exchange(
                "http://localhost:8080/films", HttpMethod.POST,
                // Send HeaderHttp within the HttpEntity
                new HttpEntity<>(indiana, createJsonHeader()),
                Film.class);
        log.info("Saved filme: {}", indianaSaved);

    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
