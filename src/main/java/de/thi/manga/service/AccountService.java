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

@Stateless
public class AccountService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Account> findAll() {
        TypedQuery<Account> query = entityManager.createQuery("SELECT u FROM Account as u", Account.class);
        return query.getResultList();
    }

    public void createAccount(String name, String password, String role) throws NoSuchAlgorithmException {
        Account account = new Account();
        account.setName(name);
        account.setPassword(encodePassword(password));
        account.setRole(role);
        entityManager.persist(account);
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
