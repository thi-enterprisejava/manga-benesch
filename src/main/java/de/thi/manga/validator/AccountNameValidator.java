package de.thi.manga.validator;

import de.thi.manga.service.AccountService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
public class AccountNameValidator implements Validator {

    @EJB
    private AccountService accountService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) {
        String accountName = value.toString();

        if (!accountService.isAccountNameValid(accountName)) {
            throw new ValidatorException(new FacesMessage("Ungültiger Accountname"));
        }

        if (accountService.isAccountExists(accountService.transformAccountName(accountName))) {
            throw new ValidatorException(new FacesMessage("Der Account existiert bereits."));
        }
    }
}
