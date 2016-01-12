package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Location("searcht.xhtml")
public class SearchPage {

    @FindBy(className = "searchResult")
    List<WebElement> searchResults;

    public void assertThatListIsEmpty() {
        assertTrue(searchResults.isEmpty());
    }

    public void assertThatListContainsMangaTitle(String title) {
        for (WebElement mangaListEntry : searchResults) {
            if (mangaListEntry.findElement(By.className("mangaDetails")).getText()
                    .equals(title)) {
                return;
            }
        }
        fail("Manga mit Titel " + title + " ist nicht in der Liste.");
    }
}
