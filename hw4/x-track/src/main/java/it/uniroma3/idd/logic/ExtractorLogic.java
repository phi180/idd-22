package it.uniroma3.idd.logic;

import it.uniroma3.idd.domain.ExtractedData;
import it.uniroma3.idd.download.PageDownloader;
import it.uniroma3.idd.download.impl.HtmlUnitPageDownloader;
import it.uniroma3.idd.enums.DownloadEngine;
import it.uniroma3.idd.utils.PropertiesReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.util.List;

import static it.uniroma3.idd.utils.PropertiesConstants.*;

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
