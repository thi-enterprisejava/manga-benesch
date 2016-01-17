package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("register.xhtml")
public class RegisterPage {

    @FindBy(id = "accountName")
    WebElement accountNameElement;

    @FindBy(id = "displayName")
    WebElement displayNameElement;

    @FindBy(id = "password")
    WebElement passwordElement;

    @FindBy(id = "passwordConfirm")
    WebElement passwordConfirmElement;

    @FindBy(id = "registerButton")
    WebElement registerButtonElement;

    public void doRegister(String accountName, String displayName, String password) {
        accountNameElement.sendKeys(accountName);
        displayNameElement.sendKeys(displayName);
        passwordElement.sendKeys(password);
        passwordConfirmElement.sendKeys(password);
        registerButtonElement.click();
    }
}
