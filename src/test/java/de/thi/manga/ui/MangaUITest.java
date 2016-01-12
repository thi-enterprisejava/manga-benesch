package de.thi.manga.ui;


import de.thi.manga.ui.pages.AddPage;
import de.thi.manga.ui.pages.DetailsPage;
import de.thi.manga.ui.pages.EditPage;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.Collections;

@RunWith(Arquillian.class)
public class MangaUITest extends AbstractUITest {

    @Page
    DetailsPage detailsPage;

    @Page
    EditPage editPage;

    @ArquillianResource
    URL deploymentUrl;

    @Drone
    WebDriver browser;

    @Test
    public void testCreateManga(@InitialPage AddPage addPage) {
        addPage.doAddManga("Akira", "autor", "publisher",
                2012, 2013, 10, Collections.singletonList("Action"), "description");

        detailsPage.assertTitleEquals("Akira");
        detailsPage.assertAuthorEquals("autor");
        detailsPage.assertPublisherEquals("publisher");
        detailsPage.assertRunFromYearEquals("2012");
        detailsPage.assertRunUntilYearEquals("2013");
        detailsPage.assertVolumesEquals("10");
        detailsPage.assertGenresContains("Action");
        detailsPage.assertDescriptionEquals("description");
    }

    @Test
    public void testEditManga(@InitialPage AddPage addPage) {
        addPage.doAddManga("Bkira", "autor", "publisher",
                2012, 2013, 10, Collections.singletonList("Action"), "description");

        String currentUrl = browser.getCurrentUrl();
        String mangaId = currentUrl.substring(currentUrl.indexOf("=") + 1);
        System.out.println(mangaId);

        browser.get(deploymentUrl.toExternalForm() + "edit.xhtml?manga=" + mangaId);
        System.out.println(browser.getPageSource());
        editPage.doEditManga("Ckira", "autor2", "publisher2",
                2002, 2003, 20, Collections.singletonList("Horror"), "description2");
        System.out.println(browser.getPageSource());

        detailsPage.assertTitleEquals("Ckira");
        detailsPage.assertAuthorEquals("autor2");
        detailsPage.assertPublisherEquals("publisher2");
        detailsPage.assertRunFromYearEquals("2002");
        detailsPage.assertRunUntilYearEquals("2003");
        detailsPage.assertVolumesEquals("20");
        detailsPage.assertGenresContains("Horror");
        detailsPage.assertDescriptionEquals("description2");
    }

}
