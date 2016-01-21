package de.thi.manga.web.model;

import de.thi.manga.domain.Manga;
import de.thi.manga.domain.MangaList;
import de.thi.manga.service.MangaListService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class MangaListBean implements Serializable {

    @EJB
    private transient MangaListService mangaListService;

    @Inject
    private transient AccountBean account;

    private MangaList mangaList;

    @PostConstruct
    public void init() {
        Long accountId = account.getAccountId();
        try {
            mangaList = mangaListService.findMangaList(accountId);
        } catch (EJBException e) {
            //Keine Mangalist für die AccountId in der Datenbank -> neue anlegen
            mangaList = new MangaList();
            mangaList.setAccountId(accountId);
            mangaList.setMangas(new ArrayList<>());
            mangaListService.create(mangaList);
        }
    }

    public List<Manga> getMangas() {
        List<Manga> mangas = mangaList.getMangas();
        mangas.sort((m1, m2) -> m1.getTitle().compareTo(m2.getTitle()));
        return mangas;
    }

    public void addManga(Manga manga) {
        List<Manga> mangas = getMangas();
        if (!isMangaOnMangaList(manga)) {
            mangas.add(manga);
            mangaList = mangaListService.update(mangaList);
        }
    }

    public void removeManga(Manga manga) {
        if (getMangas().removeIf(mangaInList -> mangaInList.getId() == manga.getId())) {
            mangaList = mangaListService.update(mangaList);
        }
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
