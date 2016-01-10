package de.thi.manga.exception;

/**
 * Created by daniel on 10.01.16.
 */
public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("Der Benutzer ist nicht eingeloggt");
    }
}
