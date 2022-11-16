package it.uniroma3.idd.hw3.engine;

import it.uniroma3.idd.hw3.stats.Statistics;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class StatisticsCache {

    private static StatisticsCache instance = null;

    private StatisticsCache() {
        this.dataset2statistics = new HashMap<>();
    }

    public static StatisticsCache getInstance() {
        if(instance == null)
            instance = new StatisticsCache();

        return instance;
    }

    @Getter
    @Setter
    private Map<String, Statistics> dataset2statistics;

    public void addStatistics(String datasetPath, Statistics statistics) {
        this.dataset2statistics.put(datasetPath,statistics);
    }

    public void cleanCache() {
        this.dataset2statistics = new HashMap<>();
    }

}
