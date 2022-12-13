package it.uniroma3.idd;

import it.uniroma3.idd.hw3.api.*;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.hw3.logic.result.QueryResultsWriter;
import it.uniroma3.idd.hw3.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    private static final Integer K = 5;

    public static void main(String[] args) {
        String datasetPath = args[0];
        int nQueries = Integer.parseInt(args[1]);

        buildIndex(datasetPath);


        for(int i = 0; i<nQueries;i++) {
            RandomApi randomApi = new RandomApiImpl();
            ColumnVO columnVO = randomApi.getRandomColumn(datasetPath);

            QueryApi queryApi = new QueryApiImpl();
            Long beginQueryTime = new Date().getTime();
            List<ResultVO> resultVOList = queryApi.query(columnVO, K);
            Utils.writeResultsToFile(Utils.getListOfTokens(columnVO), resultVOList, beginQueryTime);
        }

    }

    private static void buildIndex(String datasetPath) {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.createIndex(datasetPath);
    }

    private static String[] splitToTokens(String cellContent) {
        return cellContent.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }

}