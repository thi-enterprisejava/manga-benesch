package de.thi.manga.ui.pages;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Location("mangalist.xhtml")
public class MangaListPage {

    @FindBy(className = "mangalistEntry")
    List<WebElement> mangalistEntries;

    public void assertThatListIsEmpty() {
        assertTrue(mangalistEntries.isEmpty());
    }

    public void assertThatListContainsMangaTitle(String title) {
        for(WebElement mangaListEntry : mangalistEntries) {
            if(mangaListEntry.findElement(By.className("mangaDetails")).getText()
                    .equals(title)) {
                return;
            }
        }
        fail("Manga mit Titel "+title+" ist nicht in der Liste.");
    }
}
