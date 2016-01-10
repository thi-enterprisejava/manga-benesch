package de.thi.manga.web.model;


import de.thi.manga.domain.Account;
import de.thi.manga.service.AccountService;
import de.thi.manga.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountBeanTest {

    @Spy
    AccountService accountService;

    @Mock
    HttpServletRequest httpServletRequest;

    @InjectMocks
    AccountBean accountBean;

    private Account loggedInAccount;


    @Before
    public void setUp() {
        loggedInAccount = new Account();
        loggedInAccount.setId(1L);
        loggedInAccount.setName("user");
        loggedInAccount.setDisplayName("User");
    }

    @Test
    public void testIsNotLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn(null);
        TestUtils.postConstruct(accountBean);

        assertFalse(accountBean.isLoggedIn());
    }

    @Test
    public void testIsLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn("user");
        doReturn(loggedInAccount).when(accountService).findAccountByName(any());
        TestUtils.postConstruct(accountBean);

        assertTrue(accountBean.isLoggedIn());
    }

    @Test
    public void testAccountIdLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn("user");
        doReturn(loggedInAccount).when(accountService).findAccountByName(any());
        TestUtils.postConstruct(accountBean);

        assertEquals(Long.valueOf(1), accountBean.getAccountId());
    }

    @Test(expected = RuntimeException.class)
    public void testAccountIdNotLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn(null);

        TestUtils.postConstruct(accountBean);

        accountBean.getAccountId();
    }

    @Test
    public void testDisplayNameLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn("user");
        doReturn(loggedInAccount).when(accountService).findAccountByName(any());

        TestUtils.postConstruct(accountBean);

        assertEquals("User", accountBean.getDisplayName());
    }

    @Test(expected = RuntimeException.class)
    public void testDisplayNameNotLoggedIn() throws Exception {
        when(httpServletRequest.getRemoteUser()).thenReturn(null);

        TestUtils.postConstruct(accountBean);

        accountBean.getDisplayName();
    }

}
