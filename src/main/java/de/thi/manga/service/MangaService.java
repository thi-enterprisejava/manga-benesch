package de.thi.manga.service;

import de.thi.manga.domain.Manga;
import de.thi.manga.domain.MangaList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MangaService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Legt einen neuen Manga in der Datenbank an und macht die Instanz zu einer
     * Managed Instanz des EntityManagers.
     *
     * @param manga der neue Manga
     */
    public void create(Manga manga) {
        entityManager.persist(manga);
    }

    /**
     * Aktualisiert eine existierenden Manga in der Datenbank.
     * Weitere Änderungen am Objekt müssen über die zurückgegebene Instanz erfolgen.
     * Zum Anlegen einer neuen Manga {@link #create(Manga)} verwenden.
     *
     * @param manga der existierende Manga
     * @return die Managed Instanz des EntityManagers des upgedateten Manga
     */
    public Manga update(Manga manga) {
        return entityManager.merge(manga);
    }

    /**
     * Löscht den Manga aus allen {@link MangaList}en und anschließend aus der Datenbank
     *
     * @param manga der Manga der gelöscht werden soll
     */
    public void delete(Manga manga) {
        Manga managedManga = entityManager.contains(manga) ? manga : entityManager.merge(manga);

        TypedQuery<MangaList> query = entityManager.createQuery("SELECT ml FROM MangaList as ml WHERE :mangaId IN (SELECT manga.id FROM ml.mangas as manga)", MangaList.class);
        query.setParameter("mangaId", managedManga.getId());
        List<MangaList> resultList = query.getResultList();
        for (MangaList mangaList : resultList) {
            mangaList.getMangas().remove(managedManga);
            entityManager.merge(mangaList);
        }
        entityManager.remove(managedManga);
    }

    public List<Manga> findAll() {
        TypedQuery<Manga> query = entityManager.createQuery("SELECT m FROM Manga as m", Manga.class);
        return query.getResultList();
    }

    public List<Manga> findByTitle(String title) {
        TypedQuery<Manga> query = entityManager.createQuery("SELECT m FROM Manga as m WHERE m.title LIKE :title", Manga.class);
        query.setParameter("title", title + "%");
        return query.getResultList();
    }

    public List<Manga> findByGenreId(Long genreId) {
        TypedQuery<Manga> query = entityManager.createQuery("SELECT m FROM Manga as m WHERE :genreId IN (SELECT g.id FROM m.genres as g)", Manga.class);
        query.setParameter("genreId", genreId);
        return query.getResultList();
    }

    public List<Manga> findByTitleAndGenreId(String title, Long genreId) {
        TypedQuery<Manga> query = entityManager.createQuery("SELECT m FROM Manga as m WHERE m.title LIKE :title AND :genreId IN (SELECT g.id FROM m.genres as g)", Manga.class);
        query.setParameter("title", title + "%");
        query.setParameter("genreId", genreId);
        return query.getResultList();
    }

    public Manga findById(Long id) {
        return entityManager.find(Manga.class, id);
    }

}
