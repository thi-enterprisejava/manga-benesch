package de.thi.manga.web.model;

import de.thi.manga.domain.Genre;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@ManagedBean
public class GenreWrapper {

    public Genre[] getGenres() {
        return Genre.values();
    }

}
