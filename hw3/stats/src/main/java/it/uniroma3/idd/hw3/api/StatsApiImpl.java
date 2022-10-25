package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.api.StatsApi;
import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.hw3.engine.Engine;
import it.uniroma3.idd.hw3.stats.Statistics;

public class StatsApiImpl implements StatsApi {

    @Override
    public StatisticsVO runStatistics(String datasetPath) {
        Engine engine = new Engine();
        return getStatisticsVO(engine.run(datasetPath));
    }

    /* PRIVATE METHODS **/

    private StatisticsVO getStatisticsVO(Statistics statistics) {
        StatisticsVO statisticsVO = new StatisticsVO();

        statisticsVO.setNumberOfTables(statistics.getNumberOfTables());
        statisticsVO.setTotalNumberOfRows(statistics.getTotalNumberOfRows());
        statisticsVO.setTotalNumberOfColumns(statistics.getTotalNumberOfColumns());
        statisticsVO.setNumberOfEmptyCells(statistics.getNumberOfEmptyCells());

        statisticsVO.setNumOfRows2numOfTables(statistics.getNumOfRows2numOfTables());
        statisticsVO.setNumOfColumns2numOfTables(statistics.getNumOfColumns2numOfTables());
        statisticsVO.setNumOfDistinctValues2numberOfColumns(statistics.getNumOfDistinctValues2numberOfColumns());

        statisticsVO.setMeanNumberOfRowsForTable(statistics.getMeanNumberOfRowsForTable());
        statisticsVO.setMeanNumberOfEmptyCells(statistics.getMeanNumberOfEmptyCells());
        statisticsVO.setMeanNumberOfColumnsForTable(statistics.getMeanNumberOfColumnsForTable());
        statisticsVO.setMeanDistinctElementsInColumns(statistics.getMeanDistinctElementsInColumn());
        statisticsVO.setStandardDevDistinctElements2Columns(statistics.getStandardDevDistinctElements2Columns());

        return statisticsVO;
    }

}
