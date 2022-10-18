package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Experiment1 {

    /**
     * Name: Warm Up
     * Target: study index structure and query results, only term queries allowed
     * */
    private static final String DATASET_ADDR = TestUtils.getFileInResources("dataset/little");

    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "water";
    private static final String THIRD_QUERY = "cookie mix";

    private SearchApi searchApi;

    @Test
    public void runExperiment() {
        buildIndex();

        searchApi = new SearchApiImpl();

        assertEquals(1,searchApi.getAllResults(FIRST_QUERY).getResultListDTO().size());
        assertEquals(4,searchApi.getAllResults(SECOND_QUERY).getResultListDTO().size());
        assertEquals(0,searchApi.getAllResults(THIRD_QUERY).getResultListDTO().size());
    }

    /** Private methods */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR);
    }

}
