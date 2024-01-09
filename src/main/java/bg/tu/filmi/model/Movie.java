package bg.tu.filmi.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Date releaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Genre genre;
    @ManyToOne(cascade = CascadeType.ALL)
    private Language language;

    private String summary;

    public Movie() {
    }

    public Movie(Long id, String title, Date releaseDate, Genre genre, Language language, String summary) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.language = language;
        this.summary = summary;
    }

    public Movie(String title, Date releaseDate, Genre genre, Language language, String summary) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.language = language;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
