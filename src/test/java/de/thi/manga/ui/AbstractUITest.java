package de.thi.manga.ui;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;

public abstract class AbstractUITest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsWebResource(new File("src/main/webapp/add.xhtml"))
                .addAsWebResource(new File("src/main/webapp/details.xhtml"))
                .addAsWebResource(new File("src/main/webapp/edit.xhtml"))
                .addAsWebResource(new File("src/main/webapp/index.xhtml"))
                .addAsWebResource(new File("src/main/webapp/login.xhtml"))
                .addAsWebResource(new File("src/main/webapp/login-error.xhtml"))
                .addAsWebResource(new File("src/main/webapp/login-template.xhtml"))
                .addAsWebResource(new File("src/main/webapp/mangalist.xhtml"))
                .addAsWebResource(new File("src/main/webapp/notfound.xhtml"))
                .addAsWebResource(new File("src/main/webapp/register.xhtml")) //remove
                .addAsWebResource(new File("src/main/webapp/search.xhtml"))
                .addAsWebResource(new File("src/main/webapp/template.xhtml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"))
                .addAsResource(new File("src/main/resources/messages.properties"))
                .addPackage("de.thi.manga.domain")
                .addPackage("de.thi.manga.exception")
                .addPackage("de.thi.manga.service")
                .addPackage("de.thi.manga.util")
                .addPackage("de.thi.manga.validator")
                .addPackage("de.thi.manga.web.model")
                .addAsLibraries(Maven.resolver().resolve("org.omnifaces:omnifaces:2.2").withoutTransitivity().asFile())
                        //.addAsLibraries(Maven.resolver().resolve("org.mockito:mockito-all:1.10.19").withoutTransitivity().asFile())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        System.out.println(webArchive.toString(true));
        return webArchive;
    }
}
