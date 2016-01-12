package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Location("details.xhtml")
public class DetailsPage {

    @FindBy(id = "title")
    WebElement titleElement;

    @FindBy(id = "author")
    WebElement authorElement;

    @FindBy(id = "publisher")
    WebElement publisherElement;

    @FindBy(id = "runFromYear")
    WebElement runFromYearElement;

    @FindBy(id = "runUntilYear")
    WebElement runUntilYearElement;

    @FindBy(id = "volumes")
    WebElement volumesElement;

    @FindBy(className = "genre")
    List<WebElement> genresElements;

    @FindBy(id = "description")
    WebElement descriptionElement;

    @FindBy(id = "cover")
    WebElement coverElement;

    public void assertTitleEquals(String title) {
        assertEquals(title, titleElement.getText());
    }

    public void assertAuthorEquals(String author) {
        assertEquals(author, authorElement.getText());
    }

    public void assertPublisherEquals(String publisher) {
        assertEquals(publisher, publisherElement.getText());
    }

    public void assertRunFromYearEquals(String runFromYear) {
        assertEquals(runFromYear, runFromYearElement.getText());
    }

    public void assertRunUntilYearEquals(String runUntilYear) {
        assertEquals(runUntilYear, runUntilYearElement.getText());
    }

    public void assertVolumesEquals(String volumes) {
        assertEquals(volumes, volumesElement.getText());
    }

    public void assertGenresContains(String genre) {
        assertTrue(genresElements.stream().anyMatch(genreElement -> genre.equals(genreElement.getText())));
    }

    public void assertDescriptionEquals(String description) {
        assertEquals(description, descriptionElement.getText());
    }
}
