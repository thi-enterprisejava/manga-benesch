
package de.thi.manga.service;

import de.thi.manga.domain.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

@Stateless
public class AccountService {

    public static final int MAX_ACCOUNT_NAME_LENGTH = 30;
    public static final int MAX_ACCOUNT_DISPLAYNAME_LENGTH = 30;
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MAX_PASSWORD_LENGTH = 50;

    public static final String DEFAULT_ROLE = "user";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> findAll() {
        TypedQuery<Account> query = entityManager.createQuery("SELECT u FROM Account as u", Account.class);
        return query.getResultList();
    }

    public Account findAccountByName(String name) {
        TypedQuery<Account> query = entityManager.createQuery("SELECT u FROM Account as u WHERE u.name = :name", Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public void createAccount(String name, String displayName, String password) throws NoSuchAlgorithmException {
        createAccount(name, displayName, password, DEFAULT_ROLE);
    }

    public void createAccount(String name, String displayName, String password, String role) throws NoSuchAlgorithmException {
        Account account = new Account();
        account.setName(transformAccountName(name));
        account.setDisplayName(displayName);
        account.setPassword(encodePassword(password));
        account.setRole(role);
        entityManager.persist(account);
    }

    public boolean isAccountExists(String name) {
        TypedQuery<Account> query = entityManager.createQuery("SELECT u FROM Account as u WHERE u.name=:name", Account.class);
        query.setParameter("name", transformAccountName(name));
        return !query.getResultList().isEmpty();
    }

    public String transformAccountName(String name) {
        return name.toLowerCase(Locale.ENGLISH);
    }

    public boolean isAccountNameValid(String name) {
        return name.length() > 0
                && name.length() <= MAX_ACCOUNT_NAME_LENGTH
                && name.matches("[a-zA-Z0-9]*");
    }

    public boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH;
    }

    /**
     * Encodiert das Passwort entsprechend für die security Domain (siehe standalone.xml)
     *
     * @param password das Passwort im Klartext
     * @return das sha256 und base64 codierte Passwort
     * @throws NoSuchAlgorithmException
     * @see <a href="https://docs.jboss.org/jbossas/docs/Server_Configuration_Guide/4/html/Using_JBoss_Login_Modules-Password_Hashing.html">Using_JBoss_Login_Modules-Password_Hashing</a>
     */
    public String encodePassword(String password) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256").digest(password.getBytes()));
    }
}
