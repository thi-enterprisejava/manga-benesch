package de.thi.manga.service;

import de.thi.manga.domain.Manga;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MangaService {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(Manga manga) {
        entityManager.persist(manga);
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
