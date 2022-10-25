package it.uniroma3.idd.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    private static final String DATASET_PATH = Utils.getPropertiesFullPath("dataset/tables.json");

    @Test
    void testCountLines() {
        assertEquals(3, Utils.countLines(DATASET_PATH));
    }

}