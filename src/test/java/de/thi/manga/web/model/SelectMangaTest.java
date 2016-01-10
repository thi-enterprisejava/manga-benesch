package de.thi.manga.web.model;


import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SelectMangaTest {

    @Mock
    GenreService genreService;

    @Mock
    MangaService mangaService;

    @InjectMocks
    SelectedManga selectedManga;

    @Test
    public void testListGenres() throws Exception {
        List genres = Arrays.asList(new Genre("genre1"), new Genre("genre2"));
        when(genreService.findAll()).thenReturn(genres);

        List<Genre> listGenres = selectedManga.getListGenres();

        assertNotNull(listGenres);
        assertEquals(2, listGenres.size());
        //Assert sortiert nach Namen
        assertTrue("genre1".equals(listGenres.get(0).getName()));
        assertTrue("genre2".equals(listGenres.get(1).getName()));
    }

    @Test
    public void testAddManga() throws Exception {
        Part part = mock(Part.class);
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}));
        Manga manga = new Manga();
        manga.setTitle("title");
        Genre genre1 = new Genre();
        genre1.setName("genre1");
        genre1.setId(1L);
        when(genreService.findAll()).thenReturn(Arrays.asList(genre1));
        selectedManga.setManga(manga);
        selectedManga.setImageFile(part);
        selectedManga.setSelectedGenreIds(Arrays.asList("1"));
        ArgumentCaptor<Manga> argumentCaptor = ArgumentCaptor.forClass(Manga.class);

        selectedManga.doAdd();

        verify(mangaService, times(1)).add(argumentCaptor.capture());
        Manga capturedManga = argumentCaptor.getValue();
        assertEquals("title", capturedManga.getTitle());
        assertEquals(1, capturedManga.getGenres().size());
        assertEquals("genre1", capturedManga.getGenres().get(0).getName());
        assertTrue(Arrays.equals(new byte[]{1, 2, 3, 4, 5}, capturedManga.getCover()));
    }

    @Test
    public void testUpdateManga() throws Exception {
        Part part = mock(Part.class);
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 5}));
        Part part2 = mock(Part.class);
        when(part2.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{1, 2, 3}));
        Manga manga = new Manga();
        manga.setTitle("title");
        Genre genre1 = new Genre();
        genre1.setName("genre1");
        genre1.setId(1L);
        Genre genre2 = new Genre();
        genre2.setName("genre2");
        genre2.setId(2L);
        when(genreService.findAll()).thenReturn(Arrays.asList(genre1, genre2));
        selectedManga.setManga(manga);
        selectedManga.setImageFile(part);
        selectedManga.setSelectedGenreIds(Arrays.asList("1"));
        ArgumentCaptor<Manga> argumentCaptor = ArgumentCaptor.forClass(Manga.class);

        selectedManga.getManga().setTitle("new Title");
        selectedManga.setImageFile(part2);
        selectedManga.setSelectedGenreIds(Arrays.asList("2"));
        selectedManga.doUpdate();

        verify(mangaService, times(1)).update(argumentCaptor.capture());
        Manga capturedManga = argumentCaptor.getValue();
        assertEquals("new Title", capturedManga.getTitle());
        assertEquals(1, capturedManga.getGenres().size());
        assertEquals("genre2", capturedManga.getGenres().get(0).getName());
        assertTrue(Arrays.equals(new byte[]{1, 2, 3}, capturedManga.getCover()));
    }

}
