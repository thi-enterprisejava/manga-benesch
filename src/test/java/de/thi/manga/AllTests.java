package de.thi.manga;

import de.thi.manga.service.*;
import de.thi.manga.ui.MangaUITest;
import de.thi.manga.ui.SearchUITest;
import de.thi.manga.validator.AccountNameValidatorTest;
import de.thi.manga.validator.MangaCoverUploadValidatorTest;
import de.thi.manga.validator.MangaRunUntilYearValidatorTest;
import de.thi.manga.validator.PasswordValidatorTest;
import de.thi.manga.web.model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountServiceTest.class,
        AccountServiceIntegrationTest.class,
        GenreServiceIntegrationTest.class,
        MangaListServiceIntegrationTest.class,
        MangaServiceIntegrationTest.class,

        AccountNameValidatorTest.class,
        MangaCoverUploadValidatorTest.class,
        MangaRunUntilYearValidatorTest.class,
        PasswordValidatorTest.class,

        AccountBeanTest.class,
        ImageStreamerTest.class,
        MangaListBeanTest.class,
        SearchMangaTest.class,
        SelectMangaTest.class,

        //Oberflächentests
        SearchUITest.class,
        MangaUITest.class,
})
public class AllTests {

}
