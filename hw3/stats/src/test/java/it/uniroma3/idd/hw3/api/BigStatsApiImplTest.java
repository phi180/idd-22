package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.hw3.util.Utils;
import org.junit.jupiter.api.Test;

public class BigStatsApiImplTest {

    private static final String DATASET_PATH = "C:\\Users\\fippi\\datasets\\hw3\\tables.json";

    @Test
    void testRunStatistics() {
        StatsApi statsApi = new StatsApiImpl();
        statsApi.runStatistics(DATASET_PATH);
    }

}
