package it.uniroma3.idd.hw2.utils;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.search.Explanation;

import java.io.File;
import java.io.IOException;

import static it.uniroma3.idd.hw2.utils.constants.Constants.INDEX_STATS;

public class StatsWriter {
    public static void initStatsFile(String queryString, Long timestamp) {
        File statsDir = new File(INDEX_STATS);
        if (!statsDir.exists()){
            statsDir.mkdirs();
        }

        String runStatsFile = INDEX_STATS + "/stats_" + timestamp;
        String content = "Stats result for query: " + queryString + "\n";
        try {
            FileUtils.writeStringToFile(new File(runStatsFile),content, "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeStats(Long timestamp, String docTitle, Explanation explanation) {
        String runStatsFile = INDEX_STATS + "/stats_" + timestamp;
        try {
            FileUtils.writeStringToFile(new File(runStatsFile),docTitle + "\n" +explanation.toString(), "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendElapsedTimeAndHits(Long initialTimeStamp, Long endTimeStamp, Long hits) {
        String runStatsFile = INDEX_STATS + "/stats_" + initialTimeStamp;
        String content = "\nElapsed time: " + (endTimeStamp-initialTimeStamp) + "ms";
        content += "\nTotal hits: " + hits;

        try {
            FileUtils.writeStringToFile(new File(runStatsFile),content, "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
