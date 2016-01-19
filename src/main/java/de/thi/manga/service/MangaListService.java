package de.thi.manga.service;

import de.thi.manga.domain.MangaList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class MangaListService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Legt eine neue MangaListe in der Datenbank an und macht die Instanz zu einer
     * Managed Instanz des EntityManagers.
     *
     * @param mangaList die neue MangaList
     */
    public void create(MangaList mangaList) {
        entityManager.persist(mangaList);
    }

    /**
     * Aktualisiert eine existierende MangaList in der Datenbank.
     * Weitere Änderungen am Objekt müssen über die zurückgegebene Instanz erfolgen.
     * Zum Anlegen einer neuen MangaList {@link #create(MangaList)} verwenden.
     *
     * @param mangaList die existierende MangaList
     * @return die Managed Instanz des EntityManagers der upgedateten MangaList
     */
    public MangaList update(MangaList mangaList) {
        return entityManager.merge(mangaList);
    }

    public MangaList findMangaList(Long accountId) {
        TypedQuery<MangaList> query = entityManager.createQuery("SELECT m FROM MangaList as m WHERE m.accountId = :accountId", MangaList.class);
        query.setParameter("accountId", accountId);
        return query.getSingleResult();
    }

}
