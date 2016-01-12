package de.thi.manga.ui;


import de.thi.manga.ui.pages.AddPage;
import de.thi.manga.ui.pages.SearchPage;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.Collections;

@RunWith(Arquillian.class)
public class SearchUITest extends AbstractUITest {

    @ArquillianResource
    URL deploymentUrl;

    @Drone
    WebDriver browser;

    @Page
    SearchPage searchPage;

    @Test
    @Cleanup
    public void testSearchMangaWhenNoMangaExists(@InitialPage SearchPage searchPage) {
        searchPage.assertThatListIsEmpty();
    }

    @Test
    @Cleanup
    public void testSearchManga(@InitialPage AddPage addPage) {
        addPage.doAddManga("Akira", "autor", "publisher",
                2012, 2013, 10, Collections.singletonList("Action"), "description");

        browser.get(deploymentUrl.toExternalForm() + "search.xhtml");
        searchPage.assertThatListContainsMangaTitle("Akira");
        browser.get(deploymentUrl.toExternalForm() + "search.xhtml?title=Akira");
        searchPage.assertThatListContainsMangaTitle("Akira");
        browser.get(deploymentUrl.toExternalForm() + "search.xhtml?title=Bkira");
        searchPage.assertThatListIsEmpty();
    }

}
