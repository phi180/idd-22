package it.uniroma3.idd.api;

import it.uniroma3.idd.logic.impl.selenium.PageSeekerSelenium;

import java.util.List;

public class SeekerApiImpl implements SeekerApi {

    @Override
    public List<String> getEntityUrls(String rootUrl, String nextPageButtonXPath, int nSteps, String entityLinksXPath) {
        PageSeekerSelenium pageSeekerSelenium = new PageSeekerSelenium();
        return pageSeekerSelenium.getEntityUrls(rootUrl, nextPageButtonXPath, nSteps, entityLinksXPath);
    }

}
