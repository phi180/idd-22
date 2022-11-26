package it.uniroma3.idd.hw3.logic;

import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.hw3.api.*;
import it.uniroma3.idd.hw3.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class BigQueryLogicTest {

    private static final String DATASET_PATH = "C:\\Users\\fippi\\datasets\\hw3\\tables.json";

    private static final int K = 3;

    private static final int N_QUERIES = 50;

    @Test
    void executeBuildIndex() {
        buildIndex();
    }

    @Test
    void executeBigQueryTest() {
        for(int i = 0; i<N_QUERIES;i++) {
            RandomApi randomApi = new RandomApiImpl();
            ColumnVO columnVO = randomApi.getRandomColumn(DATASET_PATH);

            if (columnVO != null) {
                QueryApi queryApi = new QueryApiImpl();
                Long beginQueryTime = new Date().getTime();
                List<ResultVO> resultVOList = queryApi.query(columnVO, K);
                Utils.writeResultsToFile(Utils.getListOfTokens(columnVO), resultVOList, beginQueryTime);
                Utils.writeFullResultsToFile(Utils.getListOfTokens(columnVO), resultVOList, beginQueryTime, DATASET_PATH);
            }
        }
    }

    /* PRIVATE METHODS */

    private void buildIndex() {
        IndexApi indexApi = new IndexApiImpl();
        indexApi.createIndex(DATASET_PATH);
    }


}
