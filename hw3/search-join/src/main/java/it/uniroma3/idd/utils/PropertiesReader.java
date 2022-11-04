package it.uniroma3.idd.utils;

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

    public static String getIndexFolderPath() {
        String indexPath = null;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            indexPath = prop.getProperty("index.path");
            logger.info("getIndexFolderPath() - indexPath="+ indexPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return indexPath;
    }

    public static String getGranularity() {
        String granularity = null;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            granularity = prop.getProperty("result.granularity");
            logger.info("getGranularity() - granularity="+granularity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return granularity;
    }

    public static float getPrecision() {
        float precision = 0.0f;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            precision = Float.parseFloat(prop.getProperty("precision"));
            logger.info("getPrecision() - precision="+precision);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return precision;
    }

    public static boolean getDebugEnabled() {
        boolean debugEnabled = false;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            debugEnabled = Boolean.parseBoolean(prop.getProperty("debug.enabled"));
            logger.info("getDebugEnabled() - debugEnabled="+debugEnabled);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return debugEnabled;
    }

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
