package it.uniroma3.idd.hw4.logic;

import it.uniroma3.idd.hw4.domain.ExtractedData;
import it.uniroma3.idd.hw4.domain.ExtractedLabeledData;
import it.uniroma3.idd.hw4.download.PageDownloader;
import it.uniroma3.idd.hw4.download.impl.HtmlUnitPageDownloader;
import it.uniroma3.idd.hw4.enums.DownloadEngine;
import it.uniroma3.idd.hw4.utils.PropertiesReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.uniroma3.idd.hw4.utils.PropertiesConstants.*;

public class ExtractorLogic {

    public ExtractedData extractData(String url, List<String> xpaths) {
        ExtractedData extractedData = new ExtractedData();
        extractedData.setUrl(url);

        String html = this.getPageDownloader().download(url);
        Document document = Jsoup.parse(html);

        for(String xpathExpression : xpaths) {
            String result = Xsoup.compile(xpathExpression).evaluate(document).get();
            extractedData.getXpath2data().put(xpathExpression,result);
        }

        return extractedData;
    }

    public ExtractedLabeledData extractLabeledData(String url, Map<String,List<String>> label2xpath) {
        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();
        extractedLabeledData.setUrl(url);

        String html = this.getPageDownloader().download(url);
        Document document = Jsoup.parse(html);

        for(Map.Entry<String,List<String>> label2xpathEntry : label2xpath.entrySet()) {
            String label = label2xpathEntry.getKey();

            extractedLabeledData.getLabel2xpathData().putIfAbsent(label,new HashMap<>());
            for(String xpath: label2xpathEntry.getValue()) {
                String result = Xsoup.compile(xpath).evaluate(document).get();
                extractedLabeledData.getLabel2xpathData().get(label).put(xpath, result);
            }
        }

        return extractedLabeledData;
    }

    /** PRIVATE METHODS */

    private PageDownloader getPageDownloader() {
        PageDownloader pageDownloader = null;

        DownloadEngine pageDownloaderEngine = DownloadEngine.fromString(PropertiesReader.getProperty(DOWNLOAD_ENGINE));
        switch (pageDownloaderEngine) {
            case HTML_UNIT:
                pageDownloader = new HtmlUnitPageDownloader();
                break;
        }

        return pageDownloader;
    }

}
