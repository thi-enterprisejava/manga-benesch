package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Location("edit.xhtml")
public class EditPage {

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

    @FindBy(id = "editMangaButton")
    WebElement editMangaButtonElement;

    public void doEditManga(String title, String author, String publisher, int runFromYear,
                            int runUntilYear, int volumes, List<String> genres, String description) {
        titleElement.clear();
        titleElement.sendKeys(title);
        authorElement.clear();
        authorElement.sendKeys(author);
        publisherElement.clear();
        publisherElement.sendKeys(publisher);
        runFromYearElement.clear();
        runFromYearElement.sendKeys(String.valueOf(runFromYear));
        runUntilYearElement.clear();
        runUntilYearElement.sendKeys(String.valueOf(runUntilYear));
        volumesElement.clear();
        volumesElement.sendKeys(String.valueOf(volumes));
        for (WebElement webElement : genresElement) {
            WebElement checkbox = webElement.findElement(By.xpath(".//input[@type='checkbox']"));
            if (checkbox.isSelected()) {
                checkbox.click();
            }
            for (String genre : genres) {
                if (genre.equals(webElement.findElement(By.xpath(".//label")).getText())) {
                    checkbox.click();
                }
            }
        }
        descriptionElement.clear();
        descriptionElement.sendKeys(description);
        editMangaButtonElement.click();
    }
}
