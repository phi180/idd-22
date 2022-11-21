package it.uniroma3.idd.api;

import java.util.List;

public interface SeekerApi {

    List<String> getEntityUrls(String rootUrl, String nextPageButtonXPath, int nSteps, String entityLinksXPath);

}
