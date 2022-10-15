package it.uniroma3.idd.hw2.utils;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.search.Explanation;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class StatsWriter {

    private static final String INDEX_STATS = "./index_stats";

    public static void writeStats(String queryString, Long timestamp, Explanation explanation) {
        File statsDir = new File(INDEX_STATS);
        if (!statsDir.exists()){
            statsDir.mkdirs();
        }

        String runStatsFile = INDEX_STATS + "/stats_" + timestamp;
        try {
            FileUtils.writeStringToFile(new File(runStatsFile),explanation.toString(), "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
