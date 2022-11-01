package it.uniroma3.idd.hw3;

import it.uniroma3.idd.api.StatsApi;
import it.uniroma3.idd.hw3.api.StatsApiImpl;

public class Main {
    public static void main(String[] args) {
        StatsApi statsApi = new StatsApiImpl();
        statsApi.runStatistics(args[1]);
    }
}