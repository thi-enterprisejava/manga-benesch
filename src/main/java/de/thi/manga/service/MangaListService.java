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

    public void create(MangaList mangaList) {
        entityManager.persist(mangaList);
    }

    public MangaList update(MangaList mangaList) {
        return entityManager.merge(mangaList);
    }

    public MangaList findMangaList(Long accountId) {
        TypedQuery<MangaList> query = entityManager.createQuery("SELECT m FROM MangaList as m WHERE m.accountId = :accountId", MangaList.class);
        query.setParameter("accountId", accountId);
        return query.getSingleResult();
    }

}
