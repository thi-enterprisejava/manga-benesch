package de.thi.manga.service;

import de.thi.manga.domain.Genre;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class GenreService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre as g", Genre.class);
        return query.getResultList();
    }

    public void add(Genre genre) {
        entityManager.persist(genre);
    }
}
