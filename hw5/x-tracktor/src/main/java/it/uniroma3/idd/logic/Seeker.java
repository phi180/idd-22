package it.uniroma3.idd.logic;

import java.util.List;

public interface Seeker {

    List<String> getEntityUrls(String rootUrl, String nextPageButtonXPath, int nSteps, String entityLinksXPath);

}
