package de.thi.manga.webservice.soap;

import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

@WebService
@Stateless
public class GenreWebService {

    @EJB
    GenreService genreService;

    public List<Genre> findAll() {
        return genreService.findAll();
    }

}
