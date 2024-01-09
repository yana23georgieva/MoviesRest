package bg.tu.filmi.controller;

import bg.tu.filmi.dto.MovieDTO;
import bg.tu.filmi.exception.MovieNotFoundException;
import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Language;
import bg.tu.filmi.model.Movie;
import bg.tu.filmi.repository.GenreRepository;
import bg.tu.filmi.repository.LanguageRepository;
import bg.tu.filmi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieRestController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        //@formatter:off
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST,
                        HttpMethod.HEAD, HttpMethod.OPTIONS,
                        HttpMethod.PUT, HttpMethod.DELETE)
                .build();
        //@formatter:on
    }

    @GetMapping
    ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Movie> get(@PathVariable Long id) {
        return this.movieRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Movie> post(@RequestBody MovieDTO m) {

        List<Genre> gernesList = genreRepository.findByGenreIgnoreCase(m.getGenre());
        Genre genre = gernesList.stream().findFirst().orElse(new Genre("empty"));
        Language language = languageRepository.findByLanguageIgnoreCase(m.getLanguage()).stream().findFirst().orElse(
                            new Language("empty"));

        Movie movie = this.movieRepository.save(new Movie(m.getTitle(),
                m.getReleaseDate(), genre, language, m.getSummary()));

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(uri).body(movie);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return this.movieRepository.findById(id).map(m -> {
            movieRepository.delete(m);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new MovieNotFoundException((id)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head(@PathVariable Long id) {
        return this.movieRepository.findById(id).map(exists -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Movie> put(@PathVariable Long id, @RequestBody Movie m) {
        return this.movieRepository.findById(id).map(
                existing -> {
                    Movie movie = this.movieRepository.save(new Movie(
                            existing.getId(), m.getTitle(), m.getReleaseDate(),
                            m.getGenre(), m.getLanguage(), m.getSummary()));
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest()
                            .toUriString());
                    return ResponseEntity.created(selfLink).body(movie);
                }).orElseThrow(() -> new MovieNotFoundException(id));
    }
}
