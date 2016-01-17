package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("login.xhtml")
public class LoginPage {

    @FindBy(id = "j_username")
    WebElement usernameElement;

    @FindBy(id = "j_password")
    WebElement passwordElement;

    @FindBy(id = "loginButton")
    WebElement loginButtonElement;

    public void doLogin(String accountName, String password) {
        usernameElement.sendKeys(accountName);
        passwordElement.sendKeys(password);
        loginButtonElement.click();
    }
}
