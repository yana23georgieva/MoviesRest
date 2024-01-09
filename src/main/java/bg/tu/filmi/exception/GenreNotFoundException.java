package bg.tu.filmi.exception;

public class GenreNotFoundException extends RuntimeException {
    private final Long id;

    public GenreNotFoundException(Long id) {
        super("genre-not-found-" + id);
        this.id = id;
    }

    public Long getGenreId() {
        return id;
    }
}
