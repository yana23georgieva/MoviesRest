package bg.tu.filmi.dto;

import bg.tu.filmi.model.Genre;
import bg.tu.filmi.model.Language;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class MovieDTO {

    private String title;

    private Date releaseDate;

    @ManyToOne
    private String genre;
    @ManyToOne
    private String language;

    private String summary;

    public MovieDTO() {
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
