package it.uniroma3.idd.api;

import it.uniroma3.idd.utils.Constants;
import it.uniroma3.idd.utils.ExtractorThreadExecutor;
import it.uniroma3.idd.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

class ExtractorApiImplTest {

    private static final String URLS_DIRECTORY = Utils.getResourceFullPath("./input/urls/");
    private static final String XPATHS_DIRECTORY = Utils.getResourceFullPath("./input/xpath/");
    private static final String SEPARATOR = "#";
    private static final String SLASH = "/";
    private static final String TXT_EXTENSION = ".txt";

    private static final int NUM_OF_THREADS = 1;

    @Test
    void getLabeledDataFromXpathDisfold() {
        String datasetName = "disfold";
        exec(datasetName);
    }

    @Test
    void getLabeledDataFromXpathForbes() {
        String datasetName = "forbes";
        exec(datasetName);
    }

    @Test
    void getLabeledDataFromXpathSole24Ore() {
        String datasetName = "sole24ore";
        exec(datasetName);
    }

    @Test
    void getLabeledDataFromXpathValueToday() {
        String datasetName = "valuetoday";
        exec(datasetName);
    }

    /** private methods */

    private void exec(String datasetName) {
        System.setProperty(Constants.DATASET_PROPERTY, datasetName);

        Thread[] thread = new Thread[NUM_OF_THREADS];

        Map<String, List<String>> label2xpaths = readLabel2Xpath(datasetName);
        /*Map<Integer, List<String>> thread2urls = this.thread2Urls(readUrls(datasetName));

        for(Map.Entry<Integer, List<String>> t2u : thread2urls.entrySet()) {
            thread[t2u.getKey()] = new ExtractorThreadExecutor(t2u.getValue(), label2xpaths);
            thread[t2u.getKey()].start();
        }*/
        ExtractorApi extractorApi = new ExtractorApiImpl();
        for(String url : readUrls(datasetName)) {
            extractorApi.getLabeledDataFromXpath(url, label2xpaths);
        }
    }

    private Map<String,List<String>> readLabel2Xpath(String datasetName) {
        Map<String,List<String>> label2xpaths = new HashMap<>();
        String fullPath = XPATHS_DIRECTORY + SLASH + datasetName;
        File file = new File(fullPath);

        try {
            List<String> contents = FileUtils.readLines(file, "UTF-8");

            for (String line : contents) {
                String[] linex = line.split(SEPARATOR);
                label2xpaths.putIfAbsent(linex[0], new ArrayList<>());
                label2xpaths.get(linex[0]).add(linex[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return label2xpaths;
    }

    private List<String> readUrls(String datasetName) {
        List<String> urls = new ArrayList<>();

        String fullPath = URLS_DIRECTORY + SLASH + datasetName + TXT_EXTENSION;
        File file = new File(fullPath);

        try {
            List<String> contents = FileUtils.readLines(file, "UTF-8");

            for (String line : contents) {
                urls.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;
    }

    private Map<Integer,List<String>> thread2Urls(List<String> urls) {
        Map<Integer,List<String>> thread2urls = new TreeMap<>();

        int i = 0;

        for(String url: urls) {
            thread2urls.putIfAbsent(i, new ArrayList<>());
            thread2urls.get(i).add(url);

            i = (i+1) % NUM_OF_THREADS;
        }

        return thread2urls;
    }

}