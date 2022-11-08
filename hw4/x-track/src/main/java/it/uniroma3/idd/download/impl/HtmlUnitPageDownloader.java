package it.uniroma3.idd.download.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import it.uniroma3.idd.download.PageDownloader;

import java.io.IOException;

public class HtmlUnitPageDownloader implements PageDownloader {

    @Override
    public String download(String url) {
        String pageAsText = null;

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(url);
            pageAsText = page.asNormalizedText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pageAsText;
    }
}
