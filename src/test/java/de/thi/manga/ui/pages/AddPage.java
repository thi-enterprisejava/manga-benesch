package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Location("add.xhtml")
public class AddPage {

    @FindBy(id = "title")
    WebElement titleElement;

    @FindBy(id = "imageFile")
    WebElement imageFileElement;

    @FindBy(id = "author")
    WebElement authorElement;

    @FindBy(id = "publisher")
    WebElement publisherElement;

    @FindBy(id = "runFromYear")
    WebElement runFromYearElement;

    @FindBy(id = "runUntilYearInput")
    WebElement runUntilYearElement;

    @FindBy(id = "volumes")
    WebElement volumesElement;

    @FindBy(xpath = "//table[@id='genres']//td")
    List<WebElement> genresElement;

    @FindBy(id = "description")
    WebElement descriptionElement;

    @FindBy(id = "addMangaButton")
    WebElement addMangaButtonElement;

    public void doAddManga(String title, String author, String publisher, int runFromYear,
                           int runUntilYear, int volumes, List<String> genres, String description) {
        titleElement.sendKeys(title);
        authorElement.sendKeys(author);
        publisherElement.sendKeys(publisher);
        runFromYearElement.sendKeys(String.valueOf(runFromYear));
        System.out.println(runFromYearElement.getText());
        runUntilYearElement.sendKeys(String.valueOf(runUntilYear));
        System.out.println(runUntilYearElement.getText());
        volumesElement.sendKeys(String.valueOf(volumes));
        for (String genre : genres) {
            for (WebElement webElement : genresElement) {
                if (genre.equals(webElement.findElement(By.xpath(".//label")).getText())) {
                    webElement.findElement(By.xpath(".//input[@type='checkbox']")).click();
                }
            }
        }
        descriptionElement.sendKeys(description);
        addMangaButtonElement.click();
    }
}
