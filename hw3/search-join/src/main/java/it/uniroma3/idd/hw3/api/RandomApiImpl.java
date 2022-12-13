package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ColumnarTableVO;
import it.uniroma3.idd.hw3.logic.RandomAccessLogic;

import java.io.IOException;
import java.util.Random;

public class RandomApiImpl implements RandomApi {

    @Override
    public ColumnarTableVO getRandomTable(String datasetPath) {
        RandomAccessLogic randomAccessLogic = new RandomAccessLogic();
        ColumnarTableVO tableVO = null;
        try {
            tableVO = randomAccessLogic.getRandomTable(datasetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tableVO;
    }

    @Override
    public ColumnVO getRandomColumn(String datasetPath) {
        Random rand = new Random();
        ColumnarTableVO tableVO = getRandomTable(datasetPath);
        int colNum = rand.nextInt(tableVO.getMaxNumColumns());

        return tableVO.getColumns().get(colNum);
    }
}
