package de.thi.manga.util;

import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Wird verwendet um Umlaute aus der messages.properties richtig darstellen zu können.
 */
public class Messages extends ResourceBundle {

    protected static final String BUNDLE_NAME = "messages";
    protected static final String BUNDLE_EXTENSION = "properties";
    protected static final String CHARSET = "UTF-8";
    protected static final Control UTF8_CONTROL = new UTF8Control();

    public Messages() {
        Locale locale = Locale.ENGLISH;
        try {
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        } catch (NullPointerException e) {
            //Wenn kein FacesContext vorhanden ist soll es trotzdem mit Englisch funktionieren
            //z.B. für die Tests
        }
        setParent(ResourceBundle.getBundle(BUNDLE_NAME,
                locale, UTF8_CONTROL));
    }

    /**
     * Erzeugt ein neues Messages Objekt, das auf evtl. bereits gecachte Element zurückgreift.
     * Eine einzige Instanz zu erzeugen ist nicht möglich, da sich die Lokale ändern kann.
     *
     * @return eine Messages Instanz der aktuellen Locale
     */
    public static Messages getInstance() {
        return new Messages();
    }

    @Override
    protected Object handleGetObject(String key) {
        return parent.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }

    /**
     * Control, das die Properties Datei in UTF-8 lädt.
     * Kopiert aus Control#newBundle().
     * In der Zeile mit PropertyResourceBundle wurde das Charset geändert.
     */
    protected static class UTF8Control extends Control {
        @Override
        public ResourceBundle newBundle
                (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, CHARSET));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }

}
