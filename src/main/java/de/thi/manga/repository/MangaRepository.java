package de.thi.manga.repository;


import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;

import java.io.Serializable;
import java.util.List;

public interface MangaRepository extends Serializable {

    void add(Manga manga);

    List<Manga> findAll();

    List<Manga> findByTitle(String title);

    List<Manga> findByGenre(Genre genre);
}
