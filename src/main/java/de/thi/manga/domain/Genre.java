package de.thi.manga.domain;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.io.Serializable;

public enum Genre {
    Adventure, Action,
    Cyberpunk,
    Ecchi,
    Horror,
    Magical_Girl, Mecha, Mystery,
    Slice_Of_Life, Sport,

    // Target Groups:
    Shounen, Seinen,
    Shoujo, Josei,

}
