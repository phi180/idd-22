package it.uniroma3.idd.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static it.uniroma3.idd.utils.Utils.getResourceFullPath;

public class PropertiesReader {

    private static final String PROPERTIES_FILE = "application.properties";

    public static String getProperty(String propertyName) {
        String property = null;
        try (InputStream input = new FileInputStream(getResourceFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            property = prop.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return property;
    }


}
