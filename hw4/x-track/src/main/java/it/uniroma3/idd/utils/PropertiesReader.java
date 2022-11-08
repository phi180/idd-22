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

    private static final Logger logger = Logger.getLogger(PropertiesReader.class.toString());

    private static final String PROPERTIES_FILE = "application.properties";

    public static String getProperty(String propertyName) {
        String indexPath = null;
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            indexPath = prop.getProperty(propertyName);
            logger.info("getIndexFolderPath() - indexPath="+ indexPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return indexPath;
    }

    private static String getPropertiesFullPath(String filename) {
        URL resource = PropertiesReader.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }


}
