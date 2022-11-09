package it.uniroma3.idd.hw3;

import it.uniroma3.idd.hw4.api.StatsApi;
import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.hw3.api.StatsApiImpl;

public class Main {
    public static void main(String[] args) {
        StatsApi statsApi = new StatsApiImpl();
        StatisticsVO statisticsVO = statsApi.runStatistics(args[0]);
        System.out.println(statisticsVO);
    }
}