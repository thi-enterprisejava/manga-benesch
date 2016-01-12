package de.thi.manga.webservice.soap;

import de.thi.manga.domain.Manga;
import de.thi.manga.service.MangaService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
@Stateless
public class MangaWebService {

    @EJB
    MangaService mangaService;


    public List<Manga> findAll() {
        return mangaService.findAll();
    }

    public Manga findById(@WebParam(name = "mangaId") Long mangaId) {
        return mangaService.findById(mangaId);
    }

    public List<Manga> findByGenreId(@WebParam(name = "genreId") Long genreId) {
        return mangaService.findByGenreId(genreId);
    }

    public List<Manga> findByTitle(@WebParam(name = "title") String title) {
        return mangaService.findByTitle(title);
    }

    public List<Manga> findByTitleAndGenreId(@WebParam(name = "title") String title, @WebParam(name = "genreId") Long genreId) {
        return mangaService.findByTitleAndGenreId(title, genreId);
    }

}
