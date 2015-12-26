package de.thi.manga.web.model;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLogout {

    public void logout() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.invalidateSession();
        externalContext.setResponseStatus(401);
        externalContext.getResponseOutputWriter().write("<html><head><meta http-equiv='refresh' content='0;index.xhtml'/></head></html>");
        facesContext.responseComplete();
    }

}
