package de.thi.manga.exception;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("User is not logged in");
    }
}
