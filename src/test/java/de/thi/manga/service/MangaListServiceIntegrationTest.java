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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MangaListServiceIntegrationTest {

    @EJB
    MangaListService mangaListService;

    @EJB
    MangaService mangaService;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(MangaListService.class)
                .addClass(MangaService.class)
                .addClass(MangaList.class)
                .addClass(Manga.class)
                .addClass(Genre.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        return webArchive;
    }

    @Test
    @Cleanup
    public void testFindMangaList() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.add(manga1);
        Manga manga2 = new Manga();
        manga2.setTitle("manga2");
        mangaService.add(manga2);
        MangaList mangaList = new MangaList();
        mangaList.setAccountId(123L);
        mangaList.setMangas(Arrays.asList(manga1, manga2));
        mangaListService.create(mangaList);

        MangaList persistedMangaList = mangaListService.findMangaList(123L);

        assertNotNull(persistedMangaList);
        List<Manga> persistedMangasInList = persistedMangaList.getMangas();
        assertEquals(2, persistedMangasInList.size());
        assertTrue(persistedMangasInList.stream().anyMatch(manga -> "manga1".equals(manga.getTitle())));
        assertTrue(persistedMangasInList.stream().anyMatch(manga -> "manga2".equals(manga.getTitle())));
    }

    @Test
    @Cleanup
    public void testUpdateAddMangaToMangaList() throws Exception {
        MangaList mangaList = new MangaList();
        mangaList.setAccountId(123L);
        mangaList.setMangas(new ArrayList<>());
        mangaListService.create(mangaList);
        MangaList persistedMangaList = mangaListService.findMangaList(123L);

        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.add(manga1);
        List<Manga> persistedMangasInList = persistedMangaList.getMangas();
        persistedMangasInList.clear();
        persistedMangasInList.add(manga1);
        MangaList updatedMangaList = mangaListService.update(persistedMangaList);

        assertNotNull(updatedMangaList);
        List<Manga> updatedMangasInList = updatedMangaList.getMangas();
        assertEquals(1, updatedMangasInList.size());
        assertTrue(updatedMangasInList.stream().anyMatch(manga -> "manga1".equals(manga.getTitle())));
    }

    @Test
    @Cleanup
    public void testUpdateRemoveMangaToMangaList() throws Exception {
        Manga manga1 = new Manga();
        manga1.setTitle("manga1");
        mangaService.add(manga1);
        MangaList mangaList = new MangaList();
        mangaList.setAccountId(123L);
        mangaList.setMangas(new ArrayList<>(Arrays.asList(manga1)));
        mangaListService.create(mangaList);
        MangaList persistedMangaList = mangaListService.findMangaList(123L);
        assertNotNull(persistedMangaList);
        assertEquals(1, persistedMangaList.getMangas().size());

        List<Manga> persistedMangasInList = persistedMangaList.getMangas();
        persistedMangasInList.removeIf(manga -> "manga1".equals(manga.getTitle()));
        assertEquals(0, persistedMangasInList.size());
        MangaList updatedMangaList = mangaListService.update(persistedMangaList);

        assertNotNull(updatedMangaList);
        assertEquals(0, updatedMangaList.getMangas().size());
    }

}
