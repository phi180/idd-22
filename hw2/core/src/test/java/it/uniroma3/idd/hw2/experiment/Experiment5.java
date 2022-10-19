package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Experiment5 {

    /**
     * Name: Stopwords
     * Target: study index structure with stopwords and see how results change
     * */

    private static final String DATASET_ADDR = TestUtils.getFileInResources("dataset/little");
    private static final String QUERY = "the";

    private SearchApi searchApi;

    @BeforeEach
    void setUp() {
        searchApi = new SearchApiImpl();
    }

    @Test
    void testStopwords() {
        buildIndex();
        searchApi = new SearchApiImpl();
        assertEquals(0,searchApi.getAllResults(QUERY).getResultListDTO().size());
    }

    /** Private methods */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR);
    }

}
