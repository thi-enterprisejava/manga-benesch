package de.thi.manga.validator;

import de.thi.manga.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.faces.validator.ValidatorException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PasswordValidatorTest {

    @Spy
    AccountService accountService = new AccountService();

    @InjectMocks
    PasswordValidator passwordValidator;

    @Test(expected = ValidatorException.class)
    public void testPasswordIsInvalid() {
        doReturn(false).when(accountService).isPasswordValid(any());

        passwordValidator.validate(null, null, "someInvalidPassword");
    }

    @Test
    public void testPasswordIsOk() throws Exception {
        doReturn(true).when(accountService).isPasswordValid(any());

        passwordValidator.validate(null, null, "someValidPassword");
    }

}
