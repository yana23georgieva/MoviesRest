package bg.tu.filmi.controller;

import bg.tu.filmi.exception.GenreNotFoundException;
import bg.tu.filmi.exception.MovieNotFoundException;
import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Movie;
import bg.tu.filmi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreRestController {
    @Autowired
    private GenreRepository genreRepository;

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
    ResponseEntity<List<Genre>> getMovies() {
        return ResponseEntity.ok(this.genreRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Genre> get(@PathVariable Long id) {
        return this.genreRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new GenreNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Genre> post(@RequestBody Genre g) {
        Genre genre = this.genreRepository.save(new Genre(g.getGenre()));

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .buildAndExpand(genre.getId()).toUri();

        return ResponseEntity.created(uri).body(genre);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return this.genreRepository.findById(id).map(m -> {
            genreRepository.delete(m);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new GenreNotFoundException((id)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head(@PathVariable Long id) {
        return this.genreRepository.findById(id).map(exists -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new GenreNotFoundException(id));
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Genre> put(@PathVariable Long id, @RequestBody Genre g) {
        return this.genreRepository.findById(id).map(
                existing -> {
                    Genre genre = this.genreRepository.save(new Genre(existing.getId(), g.getGenre()));
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest()
                            .toUriString());
                    return ResponseEntity.created(selfLink).body(genre);
                }).orElseThrow(() -> new GenreNotFoundException(id));
    }
}
