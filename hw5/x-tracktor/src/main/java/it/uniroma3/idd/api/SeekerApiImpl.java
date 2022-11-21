package it.uniroma3.idd.api;

import it.uniroma3.idd.logic.PageSeeker;

import java.util.List;

public class SeekerApiImpl implements SeekerApi {

    @Override
    public List<String> getEntityUrls(String rootUrl, String nextPageButtonXPath, int nSteps, String entityLinksXPath) {
        PageSeeker pageSeeker = new PageSeeker();
        return pageSeeker.getEntityUrls(rootUrl, nextPageButtonXPath, nSteps, entityLinksXPath);
    }

}
