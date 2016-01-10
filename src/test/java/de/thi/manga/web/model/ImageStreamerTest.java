package de.thi.manga.web.model;


import de.thi.manga.domain.Manga;
import de.thi.manga.service.MangaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;


@RunWith(MockitoJUnitRunner.class)
public class ImageStreamerTest {

    @Spy
    MangaService mangaService;

    @InjectMocks
    ImageStreamer imageStreamer;

    @Test
    public void testImage() {
        Manga manga = new Manga();
        manga.setCover(new byte[]{1, 2, 3, 4, 5});
        doReturn(manga).when(mangaService).findById(any());

        byte[] cover = imageStreamer.getCoverByMangaId(1L);

        assertNotNull(cover);
        assertTrue(Arrays.equals(new byte[]{1, 2, 3, 4, 5}, cover));
    }

    @Test
    public void testImageNoId() {
        byte[] cover = imageStreamer.getCoverByMangaId(null);

        assertNotNull(cover);
        assertEquals(0, cover.length);
    }

    @Test
    public void testImageNoManga() {
        doReturn(null).when(mangaService).findById(any());

        byte[] cover = imageStreamer.getCoverByMangaId(1L);

        assertNotNull(cover);
        assertEquals(0, cover.length);
    }
}
