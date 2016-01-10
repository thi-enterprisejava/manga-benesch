package de.thi.manga.web.model;


import de.thi.manga.domain.Manga;
import de.thi.manga.domain.MangaList;
import de.thi.manga.service.MangaListService;
import de.thi.manga.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MangaListBeanTest {

    @Mock
    MangaListService mangaListService;

    @Mock
    AccountBean account;

    @InjectMocks
    MangaListBean mangaListBean;

    @Test
    public void testLoadExistingMangaList() throws Exception {
        MangaList mangaList = new MangaList();
        Manga manga = new Manga();
        manga.setTitle("title");
        mangaList.setMangas(Arrays.asList(manga));
        when(account.getAccountId()).thenReturn(1L);
        when(mangaListService.findMangaList(1L)).thenReturn(mangaList);

        TestUtils.postConstruct(mangaListBean);
        List<Manga> mangas = mangaListBean.getMangas();

        assertNotNull(mangas);
        assertEquals(1, mangas.size());
        assertEquals("title", mangas.get(0).getTitle());
    }

    @Test
    public void testAddManga() throws Exception {
        Manga manga = new Manga();
        manga.setId(100L);
        when(mangaListService.findMangaList(any())).thenThrow(new RuntimeException());
        when(mangaListService.update(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        TestUtils.postConstruct(mangaListBean);
        mangaListBean.addManga(manga);

        assertEquals(1, mangaListBean.getMangas().size());
    }

    @Test
    public void testAddMangaTwice() throws Exception {
        Manga manga1 = new Manga();
        manga1.setId(100L);
        Manga manga2 = new Manga();
        manga2.setId(100L);
        when(mangaListService.findMangaList(any())).thenThrow(new RuntimeException());
        when(mangaListService.update(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        TestUtils.postConstruct(mangaListBean);
        mangaListBean.addManga(manga1);
        mangaListBean.addManga(manga2);

        assertEquals(1, mangaListBean.getMangas().size());
    }
}
