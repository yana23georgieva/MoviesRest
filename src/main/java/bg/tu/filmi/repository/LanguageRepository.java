package bg.tu.filmi.repository;

import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface LanguageRepository extends JpaRepository<Language,Long> {
    Collection<Language> findByLanguageIgnoreCase(@Param("language") String language);
}
