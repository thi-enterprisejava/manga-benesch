package de.thi.manga.service;

import de.thi.manga.domain.Account;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class AccountServiceIntegrationTest {

    @EJB
    AccountService accountService;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(AccountService.class)
                .addClass(Account.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("mangatest-ds.xml");
        return webArchive;
    }

    @Test
    @Cleanup
    public void testCreateAccount() throws Exception {
        accountService.createAccount("foo", "bar", "1234", AccountService.DEFAULT_ROLE);

        Account account = accountService.findAccountByName("foo");

        assertNotNull(account);
        assertEquals("foo", account.getName());
        assertEquals("bar", account.getDisplayName());
        assertEquals(accountService.encodePassword("1234"), account.getPassword());
        assertEquals(AccountService.DEFAULT_ROLE, account.getRole());
    }

    @Test
    @Cleanup
    public void testFindAccountByName() throws Exception {
        accountService.createAccount("foo", "bar", "1234");

        assertNotNull(accountService.findAccountByName("foo"));
    }

    @Test(expected = EJBTransactionRolledbackException.class)
    @Cleanup
    public void testFindAccountByNameNoAccount() throws Exception {
        accountService.createAccount("foo", "bar", "1234");

        assertNull(accountService.findAccountByName("bar"));
    }

    @Test
    @Cleanup
    public void testAccountExists() throws Exception {
        accountService.createAccount("foo", "bar", "1234");

        assertTrue(accountService.isAccountExists("foo"));
        assertFalse(accountService.isAccountExists("bar"));
    }
}
