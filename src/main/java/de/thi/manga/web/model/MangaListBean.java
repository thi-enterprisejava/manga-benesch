package de.thi.manga.web.model;

import de.thi.manga.domain.Manga;
import de.thi.manga.domain.MangaList;
import de.thi.manga.service.MangaListService;
import de.thi.manga.service.MangaService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class MangaListBean implements Serializable {

    @EJB
    private MangaListService mangaListService;

    @EJB
    private MangaService mangaService;

    @Inject
    private AccountBean account;

    private MangaList mangaList;

    @PostConstruct
    public void init() {
        Long accountId = account.getAccountId();
        try {
            System.out.println("accountId: " + accountId);
            mangaList = mangaListService.findMangaList(accountId);
        } catch (Exception e) {
            System.out.println(e.getClass());
            mangaList = new MangaList();
            mangaList.setAccountId(accountId);
            mangaListService.create(mangaList);
        }
    }

    public List<Manga> getMangas() {
        return mangaList.getMangas();
    }

    public String addManga(Manga manga) {
        List<Manga> mangas = getMangas();
        if (!isMangaOnMangaList(manga)) {
            mangas.add(manga);
            mangaList = mangaListService.update(mangaList);
        }

        return "details.xhtml?faces-redirect=true&manga=" + manga.getId();
    }

    public String removeManga(Manga manga) {
        if (getMangas().removeIf(mangaInList -> mangaInList.getId() == manga.getId())) {
            mangaList = mangaListService.update(mangaList);
        }

        return "details.xhtml?faces-redirect=true&manga=" + manga.getId();
    }

    public boolean isMangaOnMangaList(Manga manga) {
        for (Manga mangaInList : getMangas()) {
            if (mangaInList.getId() == manga.getId()) {
                return true;
            }
        }
        return false;
    }
}
