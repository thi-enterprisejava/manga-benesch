package de.thi.manga.validator;

import org.junit.Test;
import org.mockito.Mockito;

import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import static org.mockito.Mockito.when;

public class MangaCoverUploadValidatorTest {

    MangaCoverUploadValidator mangaCoverUploadValidator = new MangaCoverUploadValidator();
    private FacesContext facesContext;

    @Test
    public void testNoCover() {
        mangaCoverUploadValidator.validate(null, null, null);
    }

    @Test(expected = ValidatorException.class)
    public void testCoverSizeTooLarge() {
        long maxSize = 1024 * 1024; //1MB
        Part part = Mockito.mock(Part.class);
        when(part.getSize()).thenReturn(maxSize + 1);

        mangaCoverUploadValidator.validate(null, null, part);
    }

    @Test(expected = ValidatorException.class)
    public void testWrongContentType() throws Exception {
        Part part = Mockito.mock(Part.class);
        when(part.getContentType()).thenReturn("text");

        mangaCoverUploadValidator.validate(null, null, part);
    }

    @Test
    public void testCorrectSizeAndType() throws Exception {
        long size = 200 * 1024; // 200kB
        Part part = Mockito.mock(Part.class);
        when(part.getSize()).thenReturn(size);
        when(part.getContentType()).thenReturn("image/jpg");

        mangaCoverUploadValidator.validate(null, null, part);
    }
}
