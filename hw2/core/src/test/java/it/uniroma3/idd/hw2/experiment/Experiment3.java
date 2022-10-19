package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.dto.ResultsDTO;
import it.uniroma3.idd.hw2.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Experiment3 {

    /**
     * Name: Ranking
     * Target: study index structure, execute 2 queries and compare them
     * */

    private static final String DATASET_ADDR = TestUtils.getFileInResources("dataset/little_bias");
    private static final String DATASET_ADDR2 = TestUtils.getFileInResources("dataset/little_bias_2");

    private static final String FIRST_QUERY = "chocolate";
    private static final String SECOND_QUERY = "minutes";

    private SearchApi searchApi = new SearchApiImpl();

    @Test
    public void runExperiment() {
        buildIndex1();
        ResultsDTO resultsDTO11 = searchApi.getAllResults(FIRST_QUERY);
        ResultsDTO resultsDTO12 = searchApi.getAllResults(SECOND_QUERY);

        buildIndex2();
        ResultsDTO resultsDTO21 = searchApi.getAllResults(FIRST_QUERY);
        ResultsDTO resultsDTO22 = searchApi.getAllResults(SECOND_QUERY);

        assertEquals(1, resultsDTO11.getResultListDTO().size());
        assertEquals(8, resultsDTO12.getResultListDTO().size());
        assertEquals(1, resultsDTO21.getResultListDTO().size());
        assertEquals(7, resultsDTO22.getResultListDTO().size());

        assertTrue(resultsDTO12.getResultListDTO().get(0).getFileName().endsWith("a little wheat bread  bread machine.txt"));
        assertTrue(resultsDTO12.getResultListDTO().get(1).getFileName().endsWith("a little of everything pork chops.txt"));
        assertTrue(resultsDTO12.getResultListDTO().get(2).getFileName().endsWith("a little more devil in these eggs.txt"));
        assertTrue(resultsDTO12.getResultListDTO().get(3).getFileName().endsWith("a little piece of heaven on a plate.txt"));

        assertTrue(resultsDTO22.getResultListDTO().get(0).getFileName().endsWith("a lulu of a wrap.txt"));
        assertTrue(resultsDTO22.getResultListDTO().get(1).getFileName().endsWith("a little wheat bread  bread machine.txt"));
        assertTrue(resultsDTO22.getResultListDTO().get(2).getFileName().endsWith("a little of everything pork chops.txt"));
        assertTrue(resultsDTO22.getResultListDTO().get(3).getFileName().endsWith("a little more devil in these eggs.txt"));
        assertTrue(resultsDTO22.getResultListDTO().get(4).getFileName().endsWith("a little piece of heaven on a plate.txt"));
    }

    /** Private methods */

    private void buildIndex1() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR);
    }

    private void buildIndex2() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR2);
    }

}
