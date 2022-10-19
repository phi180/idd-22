package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Experiment2 {

    /**
     * Name: StringField-TextField
     * Target: study index structure
     * Pre-condition: a file dummy.txt with string 'dummy' in it is needed
     * */
    private static final String DATASET_ADDR = TestUtils.getFileInResources("dataset/little");
    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "dummy";

    private SearchApi searchApi;

    //@Test
    public void runExperiment() {
        /** if you want to run this experiment, change "content" column to StringField */

        buildIndex();

        searchApi = new SearchApiImpl();

        assertEquals(0,searchApi.getAllResults(FIRST_QUERY).getResultListDTO().size());
        assertEquals(1,searchApi.getAllResults(SECOND_QUERY).getResultListDTO().size());
    }

    /** Private methods */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR);
    }

}
