package de.thi.manga.util;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

public class TestUtils {

    /**
     * Führt alle mit {@link PostConstruct} annotierten Methoden aus
     *
     * @throws Exception
     */
    public static void postConstruct(Object object) throws Exception {
        Stream.of(object.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                .forEach(method -> {
                    try {
                        method.invoke(object);
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                });
    }
}
