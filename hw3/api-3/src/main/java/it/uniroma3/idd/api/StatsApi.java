package it.uniroma3.idd.api;

import it.uniroma3.idd.entity.StatisticsVO;

public interface StatsApi {

    /**
     * @param datasetPath path on disk where dataset is stored in
     * @return statistics related to specified dataset stored in @param datasetPath
     * */
    StatisticsVO runStatistics(String datasetPath);

}
