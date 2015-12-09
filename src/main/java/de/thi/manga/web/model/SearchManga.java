package de.thi.manga.web.model;

import de.thi.manga.domain.Manga;
import de.thi.manga.service.MangaService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class SearchManga implements Serializable {

    @EJB
    private MangaService mangaService;

    private String searchString;
    private List<Manga> results;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public List<Manga> getResults() {
        return results;
    }

    public String doSearch() {
        results = mangaService.findByTitle(searchString);
        return "listResults";
    }
}
