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
public class AccountNameValidatorTest {

    @Spy
    AccountService accountService = new AccountService();

    @InjectMocks
    AccountNameValidator accountNameValidator;

    @Test(expected = ValidatorException.class)
    public void testAccountNameInvalid() {
        doReturn(false).when(accountService).isAccountExists(any());

        accountNameValidator.validate(null, null, "Minus-");
    }

    @Test(expected = ValidatorException.class)
    public void testAccountNameExists() throws Exception {
        doReturn(true).when(accountService).isAccountExists("foo");

        accountNameValidator.validate(null, null, "foo");
    }

    @Test(expected = ValidatorException.class)
    public void testAccountNameExistsLowerCase() throws Exception {
        doReturn(false).when(accountService).isAccountExists(any());
        doReturn(true).when(accountService).isAccountExists("foo");

        accountNameValidator.validate(null, null, "FOO");
    }

    @Test
    public void testAccountNameOk() throws Exception {
        doReturn(false).when(accountService).isAccountExists(any());

        accountNameValidator.validate(null, null, "foo");
    }
}
