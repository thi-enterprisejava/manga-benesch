package de.thi.manga.service;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.domain.MangaList;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MangaServiceIntegrationTest {

    @EJB
    MangaService mangaService;

    @EJB
    MangaListService mangaListService;

    @EJB
    GenreService genreService;

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(MangaService.class)
                .addClass(MangaListService.class)
                .addClass(GenreService.class)
                .addClass(Manga.class)
                .addClass(MangaList.class)
                .addClass(Genre.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        return webArchive;
    }

    @Test
    @Cleanup
    public void testFindAllMangas() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        Manga manga2 = new Manga();
        manga2.setTitle("manga2");
        mangaService.create(manga1);
        mangaService.create(manga2);

        List<Manga> mangas = mangaService.findAll();

        assertNotNull(mangas);
        assertEquals(2, mangas.size());
        assertTrue(mangas.stream().anyMatch(manga -> "manga1".equals(manga.getTitle())));
        assertTrue(mangas.stream().anyMatch(manga -> "manga2".equals(manga.getTitle())));
    }

    @Test
    @Cleanup
    public void testFindByTitle() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        Manga manga2 = new Manga();
        manga2.setTitle("manga2");
        Manga manga3 = new Manga();
        manga3.setTitle("blubb");
        mangaService.create(manga1);
        mangaService.create(manga2);
        mangaService.create(manga3);

        List<Manga> mangas = mangaService.findByTitle("manga");

        assertNotNull(mangas);
        assertEquals(2, mangas.size());
        assertTrue(mangas.stream().anyMatch(manga -> "manga1".equals(manga.getTitle())));
        assertTrue(mangas.stream().anyMatch(manga -> "manga2".equals(manga.getTitle())));
    }

    @Test
    @Cleanup
    public void testFindById() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.create(manga1);

        Manga manga = mangaService.findById(manga1.getId());

        assertNotNull(manga);
        assertEquals("manga1", manga.getTitle());
    }

    @Test
    @Cleanup
    public void testFindByGenreId() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("genre1");
        Genre genre2 = new Genre();
        genre2.setName("genre2");
        genreService.create(genre1);
        genreService.create(genre2);
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        manga1.setGenres(Collections.singletonList(genre2));
        mangaService.create(manga1);

        List<Manga> mangas = mangaService.findByGenreId(genre2.getId());

        assertNotNull(mangas);
        assertEquals(1, mangas.size());
        assertEquals("manga1", mangas.get(0).getTitle());
        assertEquals(1, mangas.get(0).getGenres().size());
        assertEquals("genre2", mangas.get(0).getGenres().get(0).getName());
    }

    @Test
    @Cleanup
    public void testFindByTitleAndGenreId() throws Exception {
        Genre genre1 = new Genre();
        genre1.setName("genre1");
        Genre genre2 = new Genre();
        genre2.setName("genre2");
        genreService.create(genre1);
        genreService.create(genre2);
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        manga1.setGenres(Collections.singletonList(genre2));
        mangaService.create(manga1);
        Manga manga2 = new Manga();
        manga2.setTitle("manga2");
        manga2.setGenres(Arrays.asList(genre1, genre2));
        mangaService.create(manga2);

        List<Manga> mangas = mangaService.findByTitleAndGenreId("manga", genre2.getId());

        assertNotNull(mangas);
        assertEquals(2, mangas.size());
        assertTrue(mangas.stream().anyMatch(manga -> "manga1".equals(manga.getTitle())));
        assertTrue(mangas.stream().anyMatch(manga -> "manga2".equals(manga.getTitle())));
        assertTrue(mangas.stream()
                .allMatch(manga -> manga.getGenres().stream()
                        .anyMatch(genre -> "genre2".equals(genre.getName()))));
    }

    @Test
    @Cleanup
    public void testUpdateManga() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.create(manga1);
        Manga persistedManga = mangaService.findById(manga1.getId());
        persistedManga.setTitle("newTitle");

        mangaService.update(persistedManga);

        Manga updatedManga = mangaService.findById(manga1.getId());
        assertNotNull(updatedManga);
        assertEquals("newTitle", updatedManga.getTitle());
    }

    @Test
    @Cleanup
    public void testDeleteManga() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.create(manga1);
        Manga persistedManga = mangaService.findById(manga1.getId());
        assertNotNull(persistedManga);
        MangaList mangaList = new MangaList();
        mangaList.setAccountId(1L);
        mangaList.setMangas(new ArrayList<>(Collections.singletonList(persistedManga)));
        mangaListService.create(mangaList);

        mangaService.delete(persistedManga);

        Manga updatedManga = mangaService.findById(manga1.getId());
        assertNull(updatedManga);
        MangaList persistedMangaList = mangaListService.findMangaList(1L);
        assertTrue(persistedMangaList.getMangas().isEmpty());
    }

}
