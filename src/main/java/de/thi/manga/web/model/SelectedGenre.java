package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by daniel on 21.11.15.
 */
@Named
@ViewScoped
public class SelectedGenre implements Serializable {

    @EJB
    private GenreService genreService;

    private String genreName;

    public void doAddGenre() {
        Genre genre = new Genre();
        genre.setName(genreName);
        genreService.add(genre);
    }

    public List<Genre> listGenres() {
        List<Genre> genres = genreService.findAll();
        genres.sort((g1, g2) -> g1.getName().compareTo(g1.getName()));
        return genres;
    }
}
