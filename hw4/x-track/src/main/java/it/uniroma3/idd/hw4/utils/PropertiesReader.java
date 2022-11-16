package it.uniroma3.idd.hw4.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static it.uniroma3.idd.hw4.utils.Utils.getResourceFullPath;

public class PropertiesReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    private static final String PROPERTIES_FILE = "application.properties";

    public static String getProperty(String propertyName) {
        String property = null;
        try (InputStream input = new FileInputStream(getResourceFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            property = prop.getProperty(propertyName);
            logger.info("getProperty() - property="+ property);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return property;
    }


}
