package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class SelectedManga implements Serializable {

    static final Logger log = Logger.getLogger("SelectedManga");

    @EJB
    private MangaService mangaService;

    @EJB
    private GenreService genreService;

    private Manga manga = new Manga();
    private Long mangaId;
    private List<Genre> genres;
    private List<String> selectedGenreIds;

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Long getMangaId() {
        return mangaId;
    }

    public void setMangaId(Long mangaId) {
        this.mangaId = mangaId;
    }

    public List<String> getSelectedGenreIds() {
        return selectedGenreIds;
    }

    public void setSelectedGenreIds(List<String> selectedGenreIds) {
        this.selectedGenreIds = selectedGenreIds;
    }

    public String doSave() {
        manga.setGenres(convertToGenreList(selectedGenreIds));

        mangaService.add(manga);
        log.info("Created manga");
        log.info("Autor: " + manga.getAuthor());
        log.info("Title: " + manga.getTitle());
        log.info("Year: " + manga.getYear());
        manga.getGenres().forEach(
                g -> log.info("Genre: " + g.getId() + ": " + g.getName()));

        return "details.xhtml?faces-redirect=true&manga=" + manga.getId();
    }

    public List<Genre> getListGenres() {
        if (genres == null) {
            genres = genreService.findAll();
            genres.sort((g1, g2) -> g1.getName().compareTo(g1.getName()));
        }
        return genres;
    }

    public void init() {
        Manga foundManga = mangaService.findById(mangaId);
        if (foundManga != null) {
            this.manga = foundManga;
        }
    }

    private List<Genre> convertToGenreList(List<String> genreIds) {
        List<Genre> genres = new ArrayList<>(genreIds.size());
        for (String genreId : genreIds) {
            long id = Long.valueOf(genreId);
            for (Iterator<Genre> iterator = this.genres.iterator(); iterator.hasNext(); ) {
                Genre next = iterator.next();
                if (id == next.getId()) {
                    genres.add(next);
                    break;
                }
            }
        }
        return genres;
    }

}
