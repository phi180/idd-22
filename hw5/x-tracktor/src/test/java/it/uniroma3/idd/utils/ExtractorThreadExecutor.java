package it.uniroma3.idd.utils;

import it.uniroma3.idd.api.ExtractorApiImpl;

import java.util.List;
import java.util.Map;

public class ExtractorThreadExecutor extends Thread {

    private List<String> urls;
    private Map<String,List<String>> label2xpaths;

    public ExtractorThreadExecutor(List<String> urls, Map<String,List<String>> label2xpaths) {
        this.urls = urls;
        this.label2xpaths = label2xpaths;
    }

    public void run() {
        for(String url : urls) {
            new ExtractorApiImpl().getLabeledDataFromXpath(url, label2xpaths);
        }
    }

}
