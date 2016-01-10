package de.thi.manga.validator;

import org.junit.Test;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.validator.ValidatorException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MangaRunUntilYearValidatorTest {

    MangaRunUntilYearValidator mangaRunUntilYearValidator = new MangaRunUntilYearValidator();

    @Test
    public void testOngoingValueEmpty() throws Exception {
        mangaRunUntilYearValidator.validate(null, null, "");
    }

    @Test
    public void testOngoingValue0() {
        mangaRunUntilYearValidator.validate(null, null, 0);
    }

    @Test(expected = ValidatorException.class)
    public void testUntilYearGreaterOrEqualFromYear() {
        UIComponent uiComponent = getRunFromYearUIComponent(2000);

        mangaRunUntilYearValidator.validate(null, uiComponent, 1999);
    }

    @Test(expected = ValidatorException.class)
    public void testUntilYearLessCurrentYear() {
        UIComponent uiComponent = getRunFromYearUIComponent(2000);

        mangaRunUntilYearValidator.validate(null, uiComponent, 3000);
    }

    @Test(expected = ValidatorException.class)
    public void testInvalidYear() {
        mangaRunUntilYearValidator.validate(null, null, "blubb");
    }

    @Test
    public void testOk() {
        UIComponent uiComponent = getRunFromYearUIComponent(2000);

        mangaRunUntilYearValidator.validate(null, uiComponent, 2010);
    }

    private UIComponent getRunFromYearUIComponent(int year) {
        UIComponent uiComponent = mock(UIComponent.class);
        UIInput uiInput = mock(UIInput.class);
        Map attributes = new HashMap<>();
        attributes.put("runFromYear", uiInput);
        when(uiComponent.getAttributes()).thenReturn(attributes);
        when(uiInput.getValue()).thenReturn(year);
        return uiComponent;
    }

}
