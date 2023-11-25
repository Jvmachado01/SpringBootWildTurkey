package turkey.wild.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.domain.WildTurkeyUser;

import java.util.List;

public interface WildTurkeyUserRepository extends JpaRepository<WildTurkeyUser, Long> {

    WildTurkeyUser findByUsername(String username);
}
