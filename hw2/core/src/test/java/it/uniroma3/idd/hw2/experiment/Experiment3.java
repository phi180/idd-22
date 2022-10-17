package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexBuildApi;
import it.uniroma3.idd.hw2.api.IndexBuildApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import org.junit.jupiter.api.Test;

public class Experiment3 {

    /**
     * Name: Ranking
     * Target: study index structure, execute 2 queries and compare them
     * */

    private static final String DATASET_ADDR = "C:\\Users\\fippi\\datasets\\hw2\\little_bias";
    private static final String DATASET_ADDR2 = "C:\\Users\\fippi\\datasets\\hw2\\little_bias_2";


    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "minutes";

    private SearchApi searchApi = new SearchApiImpl();

    @Test
    public void runExperiment() {
        buildIndex1();
        searchApi.getAllResults(FIRST_QUERY);
        searchApi.getAllResults(SECOND_QUERY);

        buildIndex2();
        searchApi.getAllResults(FIRST_QUERY);
        searchApi.getAllResults(SECOND_QUERY);
    }

    /** Private methods */

    private void buildIndex1() {
        IndexBuildApi indexBuildApi = new IndexBuildApiImpl();
        indexBuildApi.buildIndex(DATASET_ADDR);
    }

    private void buildIndex2() {
        IndexBuildApi indexBuildApi = new IndexBuildApiImpl();
        indexBuildApi.buildIndex(DATASET_ADDR2);
    }

}
