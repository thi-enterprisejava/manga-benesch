package de.thi.manga.webservice.rest;

import de.thi.manga.domain.Manga;
import de.thi.manga.service.MangaService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/mangas")
public class MangaRestService {

    @EJB
    MangaService mangaService;

    @GET
    @Produces("application/json")
    public List<Manga> findAll() {
        return mangaService.findAll();
    }

    @GET
    @Path("/{mangaId}")
    @Produces("application/json")
    public Manga findById(@PathParam("mangaId") Long mangaId) {
        return mangaService.findById(mangaId);
    }

    @GET
    @Path("/genre/{genreId}")
    @Produces("application/json")
    public List<Manga> findByGenreId(@PathParam("genreId") Long genreId) {
        return mangaService.findByGenreId(genreId);
    }

    @GET
    @Path("/title/{title}")
    @Produces("application/json")
    public List<Manga> findByTitle(@PathParam("title") String title) {
        return mangaService.findByTitle(title);
    }

    @GET
    @Path("/title/{title}/{genreId}")
    @Produces("application/json")
    public List<Manga> findByTitleAndGenreId(@PathParam("title") String title, @PathParam("genreId") Long genreId) {
        return mangaService.findByTitleAndGenreId(title, genreId);
    }

    @PUT
    @Consumes("application/json")
    public Response add(Manga manga) {
        mangaService.create(manga);
        return Response.accepted().build();
    }

    @POST
    @Consumes("application/json")
    public Response update(Manga manga) {
        mangaService.update(manga);
        return Response.accepted().build();
    }

    @DELETE
    @Consumes("application/json")
    public Response delete(Manga manga) {
        mangaService.delete(manga);
        return Response.accepted().build();
    }
}
