package it.uniroma3.idd.hw2.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexApiImplTest {

    private static final String dirPath = "src/test/resources/dummy";

    private IndexApi indexApi;

    @BeforeEach
    void setUp() {
        indexApi = new IndexApiImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test // not a test
    void testBuildIndex() {
        indexApi.buildIndex(dirPath);
    }
}