package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.service.AccountService;
import de.thi.manga.service.GenreService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.security.NoSuchAlgorithmException;

@Singleton
@Startup
public class Init {

    private static final Logger LOGGER = Logger.getLogger(Init.class);

    @EJB
    private transient GenreService genreService;

    @EJB
    private transient AccountService userService;

    @PostConstruct
    public void init() {
        initGenres();
        initUsers();
    }

    public void initGenres() {
        if (!genreService.findAll().isEmpty()) {
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

        LOGGER.debug("Genres initialized:");
        LOGGER.debug(genreService.findAll().toString());
    }

    public void initUsers() {
        if (!userService.findAll().isEmpty()) {
            return;
        }

        try {
            userService.createAccount("daniel", "Daniel", "1234", AccountService.DEFAULT_ROLE);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Could not init users", e);
        }
    }
}
