package de.thi.manga.util;

import de.thi.manga.service.GenreService;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cgenreConverter")
public class GenreConverter implements Converter {

    @EJB
    private GenreService genreService;

    public Object getAsObject(FacesContext facesContext,
                              UIComponent component, String value) {
        Long id = Long.valueOf(value);
        return genreService.findById(id);
        /*
        for(Iterator<Genre> iterator = genres.iterator(); iterator.hasNext();) {
            Genre next = iterator.next();
            if(id == next.getId()) {
                return next;
            }
        }
        return null;
        */
    }

    public String getAsString(FacesContext facesContext,
                              UIComponent component, Object object) {
        return object.toString();
    }
}
