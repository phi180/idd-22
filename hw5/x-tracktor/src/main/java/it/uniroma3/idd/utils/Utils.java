package it.uniroma3.idd.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    public static String getDriversLocation() {
        if(OSValidator.getOS().equals("windows")) {
            return Constants.WINDOWS_DRIVER_LOCATION;
        } else if(OSValidator.getOS().equals("linux")) {
            return Constants.LINUX_DRIVER_LOCATION;
        }

        return "err";
    }

    public static String getResourceFullPath(String filename) {
        URL resource = Utils.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }

    public static void writeUrlsToFile(String datasetName, List<String> urls) {
        final String URLS_FILE = getResourceFullPath("./input/urls/" + datasetName + ".txt");
        try {
            for(String url : urls) {
                FileUtils.write(new File(URLS_FILE), url + "\n", "UTF-8", true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
