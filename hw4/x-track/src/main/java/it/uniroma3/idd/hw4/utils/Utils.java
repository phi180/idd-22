package it.uniroma3.idd.hw4.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

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

}
