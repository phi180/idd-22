package it.uniroma3.idd.api;

import it.uniroma3.idd.utils.Constants;
import it.uniroma3.idd.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorApiImplTestTable {

    private static final String XPATHS_DIRECTORY = Utils.getResourceFullPath("./input/css/");
    private static final String SEPARATOR = "\\^";
    private static final String SLASH = "/";

    @Test
    void getTableFromFT() {
        exec("ft","https://www.ft.com/ft1000-2022");
    }

    @Test
    void getTableFromCampaignIndia() {
        exec("campaignindia","https://www.campaignindia.in/article/asias-top-1000-brands-the-full-ranking-revealed/414513");
    }

    private void exec(String datasetName, String url) {
        System.setProperty(Constants.DATASET_PROPERTY, datasetName);

        ExtractorApi extractorApi = new ExtractorApiImpl();
        extractorApi.getTableFromPage(url,readLabel2Xpath(datasetName));
    }

    /** private methods */

    private Map<String, List<String>> readLabel2Xpath(String datasetName) {
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

}
