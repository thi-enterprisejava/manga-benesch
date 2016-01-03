package de.thi.manga.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String author;
    private String publisher;

    private int runFromYear;
    private int runUntilYear;
    private int volumes;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres = new ArrayList<>();

    @Lob
    private byte[] cover;

    private String description;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getRunFromYear() {
        return runFromYear;
    }

    public void setRunFromYear(int year) {
        this.runFromYear = year;
    }

    public int getRunUntilYear() {
        return runUntilYear;
    }

    public void setRunUntilYear(int runUntilYear) {
        this.runUntilYear = runUntilYear;
    }

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] image) {
        this.cover = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
