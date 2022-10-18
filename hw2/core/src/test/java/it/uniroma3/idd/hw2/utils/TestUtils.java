package it.uniroma3.idd.hw2.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TestUtils {

    public static final String getFileInResources(String filename) {
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
