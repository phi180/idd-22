package it.uniroma3.idd.hw3.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

public class Utils {

    public static String getPropertiesFullPath(String filename) {
        URL resource = Utils.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }

    public static Double getMean(Map<Integer, Integer> values2frequency) {
        double sum = 0;
        double numberOfElements = 0;
        for(Map.Entry<Integer, Integer> value2freq : values2frequency.entrySet()) {
            numberOfElements += value2freq.getValue();
            sum += value2freq.getValue()*value2freq.getKey();
        }
        return sum/numberOfElements;
    }

    public static Double getStandardDeviation(Map<Integer, Integer> values2frequency) {
        double mean = getMean(values2frequency);
        int sum = 0;
        double standardDev = 0;
        for(Map.Entry<Integer, Integer> value2freq : values2frequency.entrySet()) {
            int freq = value2freq.getValue();
            int value = value2freq.getKey();
            sum+=freq;
            standardDev += freq * Math.pow(value-mean,2);
        }
        standardDev = Math.sqrt(standardDev/sum);
        return standardDev;
    }

}
