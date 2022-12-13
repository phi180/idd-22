package it.uniroma3.idd.hw3.utils;

import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.hw3.logic.result.QueryResultsWriter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static Long countLines(String fileName) {
        long lines = 0;

        try (InputStream is = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
            lines = count;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static String getPropertiesFullPath(String filename) {
        URL resource = PropertiesReader.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return file.getAbsolutePath();
    }

    public static double getChebyshevKValue(float percentage) {
        return 1/Math.sqrt(percentage);
    }

    public static Integer countDistinctTokens(ColumnVO columnVO, float precision) {
        if(precision==1f) // avoids a lot of useless calculations
            return 0;

        Set<String> columnTokens = new HashSet<>();

        for(CellVO cellVO : columnVO.getCells().values()) {
            columnTokens.addAll(List.of(splitToTokens(cellVO.getContent())));
        }

        return columnTokens.size();
    }

    public static void writeResultsToFile(List<String[]> tokens, List<ResultVO> results, Long beginTime) {
        QueryResultsWriter qrw = new QueryResultsWriter(beginTime);
        qrw.initQueryResultsFile(tokens);

        for(ResultVO result : results) {
            qrw.appendResult(result);
        }

        qrw.closeFile();
    }

    public static List<String[]> getListOfTokens(ColumnVO columnVO) {
        List<String[]> groupsOfTokens = new ArrayList<>();
        for(CellVO cellVO : columnVO.getCells().values()) {
            String[] tokens = splitToTokens(cellVO.getContent());
            groupsOfTokens.add(tokens);
        }
        return groupsOfTokens;
    }

    /** PRIVATE METHODS */

    private static String[] splitToTokens(String text) {
        return text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }



}
