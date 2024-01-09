package bg.tu.filmi.exception;

public class LanguageNotFoundException extends RuntimeException {
    private final Long id;

    public LanguageNotFoundException(Long id) {
        super("language-not-found-" + id);
        this.id = id;
    }

    public Long getGenreId() {
        return id;
    }
}
