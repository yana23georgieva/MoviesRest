package bg.tu.filmi.controller;

import bg.tu.filmi.exception.LanguageNotFoundException;
import bg.tu.filmi.exception.MovieNotFoundException;
import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Language;
import bg.tu.filmi.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/languages")
public class LanguageRestController {
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
    ResponseEntity<List<Language>> getLanguages() {
        return ResponseEntity.ok(this.languageRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Language> get(@PathVariable Long id) {
        return this.languageRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new LanguageNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Language> post(@RequestBody Language l) {
        Language language = this.languageRepository.save(new Language(l.getLanguage()));

        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .buildAndExpand(language.getId()).toUri();

        return ResponseEntity.created(uri).body(language);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        return this.languageRepository.findById(id).map(m -> {
            languageRepository.delete(m);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new LanguageNotFoundException((id)));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head(@PathVariable Long id) {
        return this.languageRepository.findById(id).map(exists -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new LanguageNotFoundException(id));
    }

    @PutMapping(value = "/{id}")
    ResponseEntity<Language> put(@PathVariable Long id, @RequestBody Language l) {
        return this.languageRepository.findById(id).map(
                existing -> {
                    Language language = this.languageRepository.save(new Language(existing.getId(), l.getLanguage()));
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest()
                            .toUriString());
                    return ResponseEntity.created(selfLink).body(language);
                }).orElseThrow(() -> new LanguageNotFoundException(id));
    }
}
