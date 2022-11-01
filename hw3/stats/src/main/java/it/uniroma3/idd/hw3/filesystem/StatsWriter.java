package it.uniroma3.idd.hw3.filesystem;

import it.uniroma3.idd.hw3.constants.Constants;
import it.uniroma3.idd.hw3.stats.Statistics;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class StatsWriter {

    public static void writeStatistics(Statistics statistics, Long beginTime) {
        createTargetFolder();

        String completeFile = "stats_" + new Date().getTime() + ".txt";
        Long endTime = new Date().getTime();

        try {
            FileUtils.writeStringToFile(new File(getTargetFullPath(completeFile)), statistics.toString() + "\n", "UTF-8");
            FileUtils.writeStringToFile(new File(getTargetFullPath(completeFile)), "Elapsed time: " + (endTime - beginTime) + "ms\n", "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* PRIVATE METHODS */

    private static void createTargetFolder() {
        try {
            FileUtils.forceMkdir(new File(Constants.TARGET_FOLDER));
            FileUtils.forceMkdir(new File(Constants.OUTSTAT_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String getTargetFullPath(String filename) {
        File file = new File(Constants.OUTSTAT_FOLDER + "/" + filename);
        return file.getAbsolutePath();
    }

}
