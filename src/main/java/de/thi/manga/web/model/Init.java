package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.service.AccountService;
import de.thi.manga.service.GenreService;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.security.NoSuchAlgorithmException;

@ManagedBean(eager = true)
@ApplicationScoped
public class Init {

    private final static Logger logger = Logger.getLogger(Init.class);

    @EJB
    private GenreService genreService;

    @EJB
    private AccountService userService;

    @PostConstruct
    public void init() {
        initGenres();
        initUsers();
    }

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

        logger.debug(genreService.findAll().toString());
    }

    public void initUsers() {
        if (userService.findAll().size() > 0) {
            return;
        }

        try {
            userService.createAccount("daniel", "1234", "user");
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Could not init users", e);
        }
    }
}
