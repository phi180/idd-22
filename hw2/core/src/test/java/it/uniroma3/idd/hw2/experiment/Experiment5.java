package it.uniroma3.idd.hw2.experiment;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Experiment5 {

    /**
     * Name: Medium datasets
     * Target: study performance with several index size and queries, not correctness
     * */

    private static final String DATASET_ADDR_POEMS = "C:\\Users\\fippi\\datasets\\hw2\\poems";

    private static final String DATASET_ADDR_RECIPES = "C:\\Users\\fippi\\datasets\\hw2\\recipes";

    private static final String[] QUERIES_RECIPE = {"chocolate","cookie","cake","pasta","dragon","java","google"};

    private static final String[] QUERIES_POEMS = {"love","anger","black","red","death","kiss","mouse","computer"};

    private SearchApi searchApi;

    @BeforeEach
    void setUp() {
        searchApi = new SearchApiImpl();
    }

    @Test
    void testPoemsDataset() {
        /* Poems dataset is 20MB large */
        buildIndexPoems();

        for(String query : QUERIES_POEMS) {
            searchApi.getAllResults(query);
        }
    }

    @Test
    void testRecipesDataset() {
        /* Recipes dataset is 200MB large */
        buildIndexRecipes();

        for(String query : QUERIES_RECIPE) {
            searchApi.getAllResults(query);
        }
    }

    @Test
    void dropIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.deleteIndex();
    }

    @Test
    void dropIndexAndStats() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.deleteIndexAndStats();
    }

    /** Private methods */

    private void buildIndexPoems() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR_POEMS);
    }

    private void buildIndexRecipes() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.buildIndex(DATASET_ADDR_RECIPES);
    }
}
