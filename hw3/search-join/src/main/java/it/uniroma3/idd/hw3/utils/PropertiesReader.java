package it.uniroma3.idd.hw3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesReader {

    private static final String PROPERTIES_FILE = "application.properties";
    private static final Logger logger = Logger.getLogger(PropertiesReader.class.toString());

    public static String getResultsDir() {
        String resultsDir = null;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            resultsDir = prop.getProperty("results.path");
            logger.info("getResultsDir() - resultsDir="+resultsDir);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultsDir;
    }

    public static String getProperty(String propertyName) {
        String propValue = null;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            propValue = prop.getProperty(propertyName);
            logger.info("getProperty() - propValue="+propValue);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return propValue;
    }

    /** PRIVATE METHODS */

    private static String getPropertiesFullPath(String filename) {
        URL resource = PropertiesReader.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        logger.info("getPropertiesFullPath() - path="+file.getAbsolutePath());
        return file.getAbsolutePath();
    }

}
