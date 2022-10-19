package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Experiment4 {

    /**
     * Name: PhraseQueries
     * Target: study differences with results given at Experiment1
     * */

    private static final String DATASET_ADDR = TestUtils.getFileInResources("dataset/little");

    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "water";
    private static final String THIRD_QUERY = "cookie mix";
    private static final String FOURTH_QUERY = "cookie";

    private SearchApi searchApi;

    @Test
    public void runExperiment() {
        buildIndex();

        searchApi = new SearchApiImpl();
        assertEquals(4,searchApi.getAllResultsPhrase(SECOND_QUERY).getResultListDTO().size());

        assertEquals(1,searchApi.getAllResultsPhrase(FIRST_QUERY).getResultListDTO().size());
        assertEquals(1,searchApi.getAllResultsPhrase(THIRD_QUERY).getResultListDTO().size());
        assertEquals(2,searchApi.getAllResultsPhrase(FOURTH_QUERY).getResultListDTO().size());
    }

    /** Private methods */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR);
    }


}
