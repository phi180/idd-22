package it.uniroma3.idd;

import it.uniroma3.idd.api.*;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.logic.result.QueryResultsWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    private static final Integer K = 5;

    public static void main(String[] args) {
        String datasetPath = args[0];
        buildIndex(datasetPath);

        RandomApi randomApi = new RandomApiImpl();
        ColumnVO columnVO = randomApi.getRandomColumn(datasetPath);

        QueryApi queryApi = new QueryApiImpl();
        Long beginQueryTime = new Date().getTime();
        List<ResultVO> resultVOList = queryApi.query(columnVO, K);
        writeResultsToFile(getListOfTokens(columnVO), resultVOList, beginQueryTime);

        System.out.println(resultVOList);
    }

    private static void buildIndex(String datasetPath) {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.createIndex(datasetPath);
    }

    private static void writeResultsToFile(List<String[]> tokens, List<ResultVO> results, Long beginTime) {
        QueryResultsWriter qrw = new QueryResultsWriter(beginTime);
        qrw.initQueryResultsFile(tokens);

        for(ResultVO result : results) {
            qrw.appendResult(result);
        }

        qrw.closeFile();
    }

    private static List<String[]> getListOfTokens(ColumnVO columnVO) {
        List<String[]> groupsOfTokens = new ArrayList<>();
        for(CellVO cellVO : columnVO.getCells().values()) {
            String[] tokens = splitToTokens(cellVO.getContent());
            groupsOfTokens.add(tokens);
        }
        return groupsOfTokens;
    }

    private static String[] splitToTokens(String cellContent) {
        return cellContent.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }

}