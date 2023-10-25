package turkey.wild.springboot.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // To generate: toString, get and set, equal and hashCode.
@AllArgsConstructor // To generate: constructor with all values: id and name;
@NoArgsConstructor
@Entity
@Builder
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
