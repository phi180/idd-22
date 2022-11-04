package it.uniroma3.idd.logic;

import it.uniroma3.idd.api.IndexApiImpl;
import it.uniroma3.idd.api.IndexApi;
import it.uniroma3.idd.domain.result.ResultColumn;
import it.uniroma3.idd.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryLogicTest {

    /*****************************************
     * ***************************************
     *     SET PRECISION = 1 IN APPLICATION.PROPERTIES
     * ***************************************
     * ***************************************
     * **/

    private static final String DATASET_PATH = Utils.getPropertiesFullPath("dataset/tables.json");

    private static final Integer K1 = 1;
    private static final Integer K2 = 2;

    @Test
    void testQueryCitta() {
        buildIndex();

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = List.of(
                new String[] {"roma"},
                new String[] {"milano"});

        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(3, results.size());
        assertEquals("citta4895843uu394", results.get(0).getTableId());
    }

    @Test
    void testQueryRegioni() {
        buildIndex();

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = List.of(
                new String[] {"lazio"},
                new String[] {"lombardia"},
                new String[] {"toscana"});

        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(2, results.size());
        assertEquals("citta4895843uu394", results.get(0).getTableId());
        assertEquals("citta4839023uu004", results.get(1).getTableId());
    }

    @Test
    void testQueryRegioni2() {
        buildIndex();

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = new ArrayList<>();
        tokens.add(new String[] {"lazio","lombardia","toscana"});

        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(2, results.size());
        // ranking not managed
        assertEquals("citta4895843uu394", results.get(0).getTableId());
        assertEquals("citta4839023uu004", results.get(1).getTableId());
    }

    @Test
    void testQueryAnni() {
        buildIndex();

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = List.of(
                new String[] {"2014"},
                new String[] {"2016"},
                new String[] {"2020"},
                new String[] {"2011"});
        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(2, results.size());
        assertEquals("ubuntu3489u934",results.get(0).getTableId());
        assertEquals("presidenti4834r439",results.get(1).getTableId());
    }


    @Test
    void testQueryAnniBis() {
        buildIndex();

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = List.of(
                new String[] {"2014"},
                new String[] {"2016"},
                new String[] {"2018"},
                new String[] {"2021"},
                new String[] {"2022"},
                new String[] {"in carica"});

        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(2, results.size());
        assertEquals("presidenti4834r439",results.get(0).getTableId());
        assertEquals("ubuntu3489u934",results.get(1).getTableId());
    }

    @Test
    void testQueryEmpty() {
        buildIndex();
        final String EMPTY_TOKEN = "";

        QueryLogic queryLogic = new QueryLogic();
        List<String[]> tokens = new ArrayList<>();
        tokens.add(new String[] {EMPTY_TOKEN});

        List<ResultColumn> results = queryLogic.query(tokens, K2);
        assertEquals(0, results.size());
    }

    /* PRIVATE METHODS */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.createIndex(DATASET_PATH);
    }

}