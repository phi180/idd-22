package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexBuildApi;
import it.uniroma3.idd.hw2.api.IndexBuildApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import org.junit.jupiter.api.Test;

public class Experiment2 {

    /**
     * Name: StringField-TextField
     * Target: study index structure
     * Pre-condition: a file dummy.txt with string 'dummy' in it is needed
     * */
    private static final String DATASET_ADDR = "C:\\Users\\fippi\\datasets\\hw2\\little";

    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "dummy";

    private SearchApi searchApi;

    @Test
    public void runExperiment() {
        buildIndex();

        searchApi = new SearchApiImpl();

        searchApi.getAllResults(FIRST_QUERY); // should return 0 results
        searchApi.getAllResults(SECOND_QUERY); // should return 1 result
    }

    /** Private methods */

    private void buildIndex() {
        IndexBuildApi indexBuildApi = new IndexBuildApiImpl();
        indexBuildApi.buildIndex(DATASET_ADDR);
    }

}
