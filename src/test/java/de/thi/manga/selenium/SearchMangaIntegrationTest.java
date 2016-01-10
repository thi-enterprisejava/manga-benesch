package de.thi.manga.selenium;


import de.thi.manga.domain.Account;
import de.thi.manga.domain.Genre;
import de.thi.manga.domain.Manga;
import de.thi.manga.service.AccountService;
import de.thi.manga.service.GenreService;
import de.thi.manga.service.MangaService;
import de.thi.manga.util.Messages;
import de.thi.manga.web.model.AccountBean;
import de.thi.manga.web.model.Init;
import de.thi.manga.web.model.SearchManga;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class SearchMangaIntegrationTest {

    @ArquillianResource
    URL deploymentUrl;
    @Drone
    WebDriver browser;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsWebResource(new File("src/main/webapp/template.xhtml"))
                .addAsWebResource(new File("src/main/webapp/index.xhtml"))
                .addAsWebResource(new File("src/main/webapp/search.xhtml"))
                .addAsWebResource(new File("src/main/webapp/notfound.xhtml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"))
                .addAsResource(new File("src/main/resources/messages.properties"))
                .addClass(Messages.class)
                .addClass(Account.class)
                .addClass(AccountBean.class)
                .addClass(AccountService.class)
                .addClass(SearchManga.class)
                .addClass(Manga.class)
                .addClass(MangaService.class)
                .addClass(Genre.class)
                .addClass(GenreService.class)
                .addClass(Init.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }

    @Test
    @RunAsClient
    public void testSearchEmpty() throws Exception {
        browser.get(deploymentUrl.toExternalForm() + "index.xhtml");
        assertEquals("Mangas", browser.getTitle());
        browser.findElement(By.id("searchTitle")).sendKeys("bbbb");
        browser.findElement(By.id("searchButton")).click();

        assertEquals(deploymentUrl.toExternalForm() + "search.xhtml?title=bbbb", browser.getCurrentUrl());
        System.out.println(browser.getPageSource());
        List<WebElement> searchResult = browser.findElements(By.className("searchResult"));
        assertNotNull(searchResult);
        assertEquals(0, searchResult.size());
    }

}
