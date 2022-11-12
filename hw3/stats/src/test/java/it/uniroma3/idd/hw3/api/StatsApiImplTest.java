package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.hw4.api.StatsApi;
import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.hw3.util.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatsApiImplTest {

    private static final String DATASET_PATH = Utils.getPropertiesFullPath("dataset/tables.json");

    @Test
    void testRunStatistics() {
        StatsApi statsApi = new StatsApiImpl();
        StatisticsVO statisticsVO = statsApi.runStatistics(DATASET_PATH);

        assertEquals(10,statisticsVO.getTotalNumberOfColumns());
        assertEquals(3,statisticsVO.getNumberOfTables());
        assertEquals(13,statisticsVO.getTotalNumberOfRows());
    }

}