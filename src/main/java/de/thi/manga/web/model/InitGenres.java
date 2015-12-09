package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class InitGenres {

    @EJB
    private GenreService genreService;

    @PostConstruct
    public void initGenres() {
        if (genreService.findAll().size() > 0) {
            return;
        }

        genreService.add(new Genre("Adventure"));
        genreService.add(new Genre("Action"));
        genreService.add(new Genre("Cyberpunk"));
        genreService.add(new Genre("Ecchi"));
        genreService.add(new Genre("Horror"));
        genreService.add(new Genre("Magical Girl"));
        genreService.add(new Genre("Mecha"));
        genreService.add(new Genre("Mystery"));
        genreService.add(new Genre("Slice of Life"));
        genreService.add(new Genre("Sport"));

        //Target Group Genres
        genreService.add(new Genre("Shounen"));
        genreService.add(new Genre("Seinen"));
        genreService.add(new Genre("Shoujo"));
        genreService.add(new Genre("Josei"));

        System.out.println(genreService.findAll().toString());
    }
}
