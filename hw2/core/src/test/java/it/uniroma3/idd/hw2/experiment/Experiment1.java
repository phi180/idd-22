package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexBuildApi;
import it.uniroma3.idd.hw2.api.IndexBuildApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import org.junit.jupiter.api.Test;

public class Experiment1 {

    /**
     * Name: Warm Up
     * Target: study index structure and query results, only term queries allowed
     * */
    private static final String DATASET_ADDR = "C:\\Users\\fippi\\datasets\\hw2\\little";

    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "water";
    private static final String THIRD_QUERY = "cookie mix";

    private SearchApi searchApi;

    @Test
    public void runExperiment() {
        buildIndex();

        searchApi = new SearchApiImpl();

        searchApi.getAllResults(FIRST_QUERY);
        searchApi.getAllResults(SECOND_QUERY);
        searchApi.getAllResults(THIRD_QUERY);
    }

    /** Private methods */

    private void buildIndex() {
        IndexBuildApi indexBuildApi = new IndexBuildApiImpl();
        indexBuildApi.buildIndex(DATASET_ADDR);
    }

}
