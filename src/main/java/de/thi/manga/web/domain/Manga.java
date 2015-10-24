package de.thi.manga.web.domain;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by daniel on 23.10.15.
 */
@Named
@SessionScoped
public class Manga implements Serializable {

    private String name;
    private String author;

    private List<Genre> genres;

    private int publishYear;
    private int bookCount;
}
