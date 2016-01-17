package de.thi.manga.util;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import java.util.concurrent.Callable;

@Stateless
@RunAs("User")
@PermitAll
public class AuthenticatedUser {

    public void call(Callable callable) throws Exception {
        callable.call();
    }

    public void run(Runnable runable) throws Exception {
        runable.run();
    }
}