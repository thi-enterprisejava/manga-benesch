package de.thi.manga.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountService accountService;

    @Before
    public void setUp() {
        accountService = new AccountService();
    }

    @Test
    public void testTransformAccountName() {
        assertEquals("blubb", accountService.transformAccountName("Blubb"));
        assertEquals("blubb123", accountService.transformAccountName("Blubb123"));
        assertEquals("ju3rg3n", accountService.transformAccountName("Ju3RG3N"));
    }

    @Test
    public void testAccountNameValidCharacters() {
        assertTrue(accountService.isAccountNameValid("Blubb"));
        assertTrue(accountService.isAccountNameValid("Blubb123"));
        assertTrue(accountService.isAccountNameValid("123blub8b"));

        assertFalse(accountService.isAccountNameValid("Blübb"));
        assertFalse(accountService.isAccountNameValid("Blübb123"));
        assertFalse(accountService.isAccountNameValid("123blub8ß"));
    }

    @Test
    public void testAccountNameValidLength() {
        assertTrue(accountService.isAccountNameValid("1"));
        assertTrue(accountService.isAccountNameValid("123456789012345678901234567890"));

        assertFalse(accountService.isAccountNameValid(""));
        assertFalse(accountService.isAccountNameValid("1234567890123456789012345678901"));
    }

    @Test
    public void testPasswordValidLength() {
        assertTrue(accountService.isPasswordValid("1234"));
        assertTrue(accountService.isPasswordValid("12345678901234567890123456789012345678901234567890"));

        assertFalse(accountService.isPasswordValid("123"));
        assertFalse(accountService.isPasswordValid("123456789012345678901234567890123456789012345678901"));
    }

    @Test
    public void testEncodePassword() throws Exception {
        assertEquals("VyM2DvEQQ6h5UgQS6a2Jfg68uZzIIOw2O/7MnXUaGpk=",
                accountService.encodePassword("god"));
        assertEquals("++pYDShru7tBMUQw1Yuoh3FqdNcTQRnFMHzcnwx6Qpk=",
                accountService.encodePassword("blubb"));
        assertEquals("XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=",
                accountService.encodePassword("password"));
        assertEquals("YqXD311hUP8lDmdGJH/zjc/0QPdjTBsNyw43uMFN6hY=",
                accountService.encodePassword("biZCaP5rmWqY0$4i9owU%-=uTr"));
    }
}
