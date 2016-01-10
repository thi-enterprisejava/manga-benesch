package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchMangaTest {

    @Mock
    GenreService genreService;

    @Mock
    MangaService mangaService;

    @InjectMocks
    SearchManga searchManga;

    @Test
    public void testListGenres() throws Exception {
        List genres = Arrays.asList(new Genre("genre1"), new Genre("genre2"));
        when(genreService.findAll()).thenReturn(genres);

        List<Genre> listGenres = searchManga.getListGenres();

        assertNotNull(listGenres);
        assertEquals(2, listGenres.size());
        //Assert sortiert nach Namen
        assertTrue("genre1".equals(listGenres.get(0).getName()));
        assertTrue("genre2".equals(listGenres.get(1).getName()));
    }

    @Test
    public void testResultsAll() throws Exception {
        searchManga.setTitle(null);
        searchManga.setGenreId(null);

        searchManga.initResults();

        verify(mangaService, times(1)).findAll();
    }

    @Test
    public void testResultsByTitle() throws Exception {
        searchManga.setTitle("title");
        searchManga.setGenreId(null);

        searchManga.initResults();

        verify(mangaService, times(1)).findByTitle("title");
    }

    @Test
    public void testResultsByGenre() throws Exception {
        searchManga.setTitle(null);
        searchManga.setGenreId(1L);

        searchManga.initResults();

        verify(mangaService, times(1)).findByGenreId(1L);
    }

    @Test
    public void testResultsByTitleAndGenre() throws Exception {
        searchManga.setTitle("title");
        searchManga.setGenreId(1L);

        searchManga.initResults();

        verify(mangaService, times(1)).findByTitleAndGenreId("title", 1L);
    }
}
