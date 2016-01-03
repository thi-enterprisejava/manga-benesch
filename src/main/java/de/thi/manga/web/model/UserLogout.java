package de.thi.manga.web.model;

import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserLogout {

    public void logout() throws Exception {
        Faces.logout();
        Faces.redirect("index.xhtml");
    }

}
