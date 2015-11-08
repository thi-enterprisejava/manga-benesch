package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.repository.MangaRepository;
import de.thi.manga.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class SelectedManga implements Serializable {

    @Inject
    private MangaRepository mangaRepository;

    private Manga manga = new Manga();
    private Genre newGenre;

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Genre getNewGenre() {
        return newGenre;
    }

    public void setNewGenre(Genre newGenre) {
        this.newGenre = newGenre;
    }

    public String getSelectedGenres() {
        Messages messages = new Messages();
        return manga.getGenres().stream().map(genre -> messages.getString("genre." + genre))
                .collect(Collectors.joining(", "));
    }

    public String doAddGenre() {
        System.out.println(manga);
        System.out.println(manga.getTitle());

        manga.getGenres().add(newGenre);
        newGenre = null;

        return null;
    }

    public String doSave() {
        mangaRepository.add(manga);

        //TODO go to detail page
        return "index.html";
    }
}
