package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Wird beim Starten der Applikation ausgeführt. Legt vordefinierte Genres für die Mangas an
 */
@Singleton
@Startup
public class Init {

    private static final Logger LOGGER = Logger.getLogger(Init.class);

    @EJB
    private transient GenreService genreService;

    @PostConstruct
    public void init() {
        initGenres();
    }

    public void initGenres() {
        if (!genreService.findAll().isEmpty()) {
            return;
        }

        genreService.create(new Genre("Adventure"));
        genreService.create(new Genre("Action"));
        genreService.create(new Genre("Cyberpunk"));
        genreService.create(new Genre("Ecchi"));
        genreService.create(new Genre("Horror"));
        genreService.create(new Genre("Magical Girl"));
        genreService.create(new Genre("Mecha"));
        genreService.create(new Genre("Mystery"));
        genreService.create(new Genre("Slice of Life"));
        genreService.create(new Genre("Sport"));

        //Target Group Genres
        genreService.create(new Genre("Shounen"));
        genreService.create(new Genre("Seinen"));
        genreService.create(new Genre("Shoujo"));
        genreService.create(new Genre("Josei"));

        LOGGER.debug("Genres initialized:");
        LOGGER.debug(genreService.findAll().toString());
    }

}
