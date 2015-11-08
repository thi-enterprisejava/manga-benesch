package de.thi.manga.repository;

import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MangaRepositoryImpl implements MangaRepository {

    private List<Manga> mangas = new ArrayList<>();

    @Override
    public void add(Manga manga) {
        mangas.add(manga);
    }

    @Override
    public List<Manga> findAll() {
        return Collections.unmodifiableList(mangas);
    }

    @Override
    public List<Manga> findByTitle(String title) {
        return mangas.stream().filter(manga -> manga.getTitle().contains(title)).collect(Collectors.toList());
    }

    @Override
    public List<Manga> findByGenre(Genre genre) {
        return mangas.stream().filter(manga -> manga.getGenres().contains(genre)).collect(Collectors.toList());
    }
}
