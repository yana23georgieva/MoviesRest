package bg.tu.filmi.repository;

import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    List<Genre> findByGenreIgnoreCase(@Param("genre") String genre);
}
