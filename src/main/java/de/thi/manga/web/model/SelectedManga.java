package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;
import org.omnifaces.util.Utils;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
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

    private Part imageFile;

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

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public String doSave() throws IOException {
        manga.setGenres(convertToGenreList(selectedGenreIds));

        if (imageFile != null) {
            manga.setCover(Utils.toByteArray(imageFile.getInputStream()));
        }

        mangaService.add(manga);
        log.info("Created manga");
        log.info("Autor: " + manga.getAuthor());
        log.info("Title: " + manga.getTitle());
        log.info("Cover: " + manga.getCover() == null ? "Nein" : "Ja");
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

    /**
     * Wird in details.xhtml aufgerufen.
     * Der Manga wird immer gesetzt und ist <i>null</i>, wenn kein Manga unter dieser mangaId angelegt ist.
     */
    public void init() {
        this.manga = mangaService.findById(mangaId);
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
