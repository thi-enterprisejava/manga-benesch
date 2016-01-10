package de.thi.manga.web.model;

import de.thi.manga.domain.Account;
import de.thi.manga.exception.NotLoggedInException;
import de.thi.manga.service.AccountService;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class AccountBean implements Serializable {

    @EJB
    private transient AccountService accountService;

    @Inject
    private transient HttpServletRequest httpServletRequest;

    private Account account;

    public boolean isLoggedIn() {
        return account != null;
    }

    public void doLogout() throws IOException, ServletException {
        Faces.logout();
        Faces.redirect("index.xhtml");
    }

    /**
     * Lädt den Account des {@link HttpServletRequest#getRemoteUser()} und speichert ihn in
     * <i>account</i>. <i>account</i> ist null wenn man nicht eingeloggt ist.
     */
    @PostConstruct
    public void loadAccount() {
        if (httpServletRequest.getRemoteUser() != null) {
            account = accountService.findAccountByName(httpServletRequest.getRemoteUser());
        }
    }

    private void assertLoggedIn() {
        if (!isLoggedIn()) {
            throw new NotLoggedInException();
        }
    }

    public Long getAccountId() {
        assertLoggedIn();
        return account.getId();
    }

    public String getDisplayName() {
        assertLoggedIn();
        return account.getDisplayName();
    }
}
