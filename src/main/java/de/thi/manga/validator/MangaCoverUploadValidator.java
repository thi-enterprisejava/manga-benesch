package de.thi.manga.validator;

import de.thi.manga.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.Part;

@Named
@RequestScoped
public class MangaCoverUploadValidator implements Validator {

    public static final int MAX_FILESIZE_MB = 1;
    public static final int MAX_FILESIZE = MAX_FILESIZE_MB * 1024 * 1024;
    public static final String CONTENT_TYPE_REGEX = "^image/.*$";

    /**
     * Für die Ausgabe in add.xhtml und edit.xhtml, da der Formatter keinen Int akzeptiert.
     */
    public static final String MAX_FILESIZE_MB_STRING = String.valueOf(MAX_FILESIZE_MB);

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) {
        Part file = (Part) value;

        if (file == null) {
            return; //kein Cover hochgeladen
        }

        if (file.getSize() > MAX_FILESIZE) {
            throw new ValidatorException(new FacesMessage(
                    new Messages().getString("de.thi.manga.validator.MangaCoverUploadValidator.fileTooLarge")
            ));
        }

        if (!file.getContentType().matches(CONTENT_TYPE_REGEX)) {
            throw new ValidatorException(new FacesMessage(
                    new Messages().getString("de.thi.manga.validator.MangaCoverUploadValidator.invalidContentType")
            ));
        }
    }
}
