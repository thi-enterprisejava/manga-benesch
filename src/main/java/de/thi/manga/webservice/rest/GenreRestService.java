package de.thi.manga.webservice.rest;

import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/genres")
public class GenreRestService {

    @EJB
    GenreService genreService;

    @GET
    @Produces("application/json")
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    @PUT
    @Consumes("application/json")
    public Response add(Genre genre) {
        genreService.create(genre);
        return Response.accepted().build();
    }

}
