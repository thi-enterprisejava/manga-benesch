package de.thi.manga.web.model;

import de.thi.manga.service.AccountService;
import org.omnifaces.util.Faces;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

@Named
@ViewScoped
public class RegisterAccount implements Serializable {

    private String accountName;
    private String password;
    private String passwordConfirm;

    @EJB
    private AccountService accountService;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String doRegister() throws NoSuchAlgorithmException, ServletException {
        accountService.createAccount(accountName, password);
        Faces.login(accountName, password);

        return "index.xhtml";
    }
}
