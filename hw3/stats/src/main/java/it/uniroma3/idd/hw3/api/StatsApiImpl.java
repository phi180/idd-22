package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.hw3.engine.Engine;
import it.uniroma3.idd.hw3.engine.StatisticsCache;
import it.uniroma3.idd.hw3.filesystem.StatsWriter;
import it.uniroma3.idd.hw3.stats.Statistics;

import java.util.Date;

public class StatsApiImpl implements StatsApi {

    @Override
    public StatisticsVO runStatistics(String datasetPath) {
        Engine engine = new Engine();
        Long beginTime = new Date().getTime();

        Statistics statistics = StatisticsCache.getInstance().getDataset2statistics().containsKey(datasetPath)?
                StatisticsCache.getInstance().getDataset2statistics().get(datasetPath): engine.run(datasetPath);
        StatisticsCache.getInstance().addStatistics(datasetPath,statistics);

        StatisticsVO statisticsVO = toStatisticsVO(statistics);

        StatsWriter.writeStatistics(statisticsVO, beginTime);
        return statisticsVO;
    }

    @Override
    public void cleanCache() {
        StatisticsCache.getInstance().cleanCache();
    }

    /* PRIVATE METHODS **/

    private StatisticsVO toStatisticsVO(Statistics statistics) {
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
        statisticsVO.setStandardDevRows2Tables(statistics.getStandardDevRows2Tables());

        return statisticsVO;
    }

}
