package bg.tu.filmi.exception;

public class MovieNotFoundException extends RuntimeException {
    private final Long id;

    public MovieNotFoundException(Long id) {
        super("movie-not-found-" + id);
        this.id = id;
    }

    public Long getMovieId() {
        return id;
    }
}
