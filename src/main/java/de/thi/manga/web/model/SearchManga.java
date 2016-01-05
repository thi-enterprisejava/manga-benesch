package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class SearchManga implements Serializable {

    @EJB
    private MangaService mangaService;

    @EJB
    private GenreService genreService;

    private String title;
    private Long genreId = null;

    private List<Manga> results;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public List<Manga> getResults() {
        return results;
    }

    public void initResults() {
        if (title == null && genreId == null) {
            results = mangaService.findAll();
        } else if (title == null) {
            results = mangaService.findByGenreId(genreId);
        } else if (genreId == null) {
            results = mangaService.findByTitle(title);
        } else {
            results = mangaService.findByTitleAndGenreId(title, genreId);
        }
    }

    public String doSearch() {
        if (title == null && genreId == null) {
            return "search.xhtml?faces-redirect=true";
        }
        if (genreId == null) {
            return "search.xhtml?faces-redirect=true&title=" + title;
        }
        return "search.xhtml?faces-redirect=true&title=" + title + "&genreId=" + genreId;
    }

    public List<Genre> getListGenres() {
        List<Genre> genres = genreService.findAll();
        genres.sort((g1, g2) -> g1.getName().compareTo(g1.getName()));
        System.out.println(genres);
        return genres;
    }
}
