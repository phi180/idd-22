package it.uniroma3.idd.hw2.api;

import it.uniroma3.idd.hw2.dto.ResultsDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchApiImplTest {

    private SearchApi searchApi;

    @BeforeEach
    void setUp() {
        this.buildIndex();

        searchApi = new SearchApiImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllResultsVolpeTest() {
        ResultsDTO results = searchApi.getAllResults("volpe");
        assertTrue(results.getResultListDTO().get(0).getFileName().endsWith("volpe.txt"));
    }

    @Test
    void getAllResultsBothTest() {
        ResultsDTO results = searchApi.getAllResults("chiostro");
        assertEquals(2, results.getResultListDTO().size());
    }

    private void buildIndex() {
        String dirPath = "src/test/resources/dummy";
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(dirPath);
    }

}