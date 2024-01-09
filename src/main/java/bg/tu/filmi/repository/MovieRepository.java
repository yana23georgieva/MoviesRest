package bg.tu.filmi.repository;

import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Language;
import bg.tu.filmi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;


public interface MovieRepository extends JpaRepository<Movie,Long> {
    Collection<Movie> findByTitleIgnoreCase(@Param("title") String title);
    Collection<Movie> findByGenreGenreIgnoreCase(@Param("genre") String genre);
//    Collection<Movie> findByReleaseDateIgnoreCase(@Param("releaseDate") Date releaseDate);
    Collection<Movie> findByLanguageLanguageIgnoreCase(@Param("language") String language);
}
