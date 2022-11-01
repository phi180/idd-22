package it.uniroma3.idd.hw3;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class TestUtils {

    private static final Logger logger = Logger.getLogger(TestUtils.class.toString());

    public static final String getPropertiesFullPath(String filename) {
        URL resource = TestUtils.class.getClassLoader().getResource(filename);
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
