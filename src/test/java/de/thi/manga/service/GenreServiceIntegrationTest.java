package de.thi.manga.service;

import de.thi.manga.domain.Genre;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GenreServiceIntegrationTest {

    @EJB
    GenreService genreService;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(GenreService.class)
                .addClass(Genre.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        return webArchive;
    }

    @Test
    @Cleanup
    public void testFindAllGenres() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("genre1");
        Genre genre2 = new Genre();
        genre2.setName("genre2");
        genreService.create(genre1);
        genreService.create(genre2);

        List<Genre> genres = genreService.findAll();

        assertNotNull(genres);
        assertEquals(2, genres.size());
        assertTrue(genres.stream().anyMatch(genre -> "genre1".equals(genre.getName())));
        assertTrue(genres.stream().anyMatch(genre -> "genre2".equals(genre.getName())));
    }

}
