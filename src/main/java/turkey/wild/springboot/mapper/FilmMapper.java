package turkey.wild.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import turkey.wild.springboot.domain.Film;
import turkey.wild.springboot.requests.FilmPostRequestBody;
import turkey.wild.springboot.requests.FilmPutRequestBody;

@Mapper(componentModel = "spring") // If you need to inject dependencies
public abstract class FilmMapper {
    public static final FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    public abstract Film toFilm(FilmPostRequestBody filmPostRequestBody);

    public abstract Film toFilm(FilmPutRequestBody filmPutRequestBody);
}
