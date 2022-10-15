package it.uniroma3.idd.hw2.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexBuildApiImplTest {

    private static final String dirPath = "src/test/resources/dummy";

    private IndexBuildApi indexBuildApi;

    @BeforeEach
    void setUp() {
        indexBuildApi = new IndexBuildApiImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test // not a test
    void testBuildIndex() {
        indexBuildApi.buildIndex(dirPath);
    }
}