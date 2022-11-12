package it.uniroma3.idd.hw3.engine;

import it.uniroma3.idd.hw4.api.ParseApi;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.hw3.api.ParseApiImpl;
import it.uniroma3.idd.hw3.filesystem.DatasetBuffer;
import it.uniroma3.idd.hw3.filesystem.StatsWriter;
import it.uniroma3.idd.hw3.stats.Statistics;

import java.io.FileNotFoundException;
import java.util.*;

public class Engine {

    private final ParseApi parseApi;

    public Engine() {
        parseApi = new ParseApiImpl();
    }

    public Statistics run(String filePath) {
        Statistics statistics = new Statistics();

        DatasetBuffer datasetBuffer = null;
        Long beginTime = new Date().getTime();

        try {
            datasetBuffer = new DatasetBuffer(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /* for each table in the dataset */
        while(!datasetBuffer.isEnded()) {
            String line = datasetBuffer.readNextLine();
            TableVO tableVO = parseApi.parse(line);
            if(tableVO == null)
                break;

            statistics.increaseNumberOfTables();
            statistics.increaseTotalNumberOfRows(tableVO.getMaxNumRows());
            statistics.increaseTotalNumberOfColumns(tableVO.getMaxNumColumns());
            statistics.increaseQuantityEmptyCells(getNumberOfEmptyCells(tableVO));

            statistics.getNumOfColumns2numOfTables().putIfAbsent(tableVO.getMaxNumColumns(),0);
            statistics.getNumOfColumns2numOfTables().put(tableVO.getMaxNumColumns(), statistics.getNumOfColumns2numOfTables().get(tableVO.getMaxNumColumns())+1);

            statistics.getNumOfRows2numOfTables().putIfAbsent(tableVO.getMaxNumRows(),0);
            statistics.getNumOfRows2numOfTables().put(tableVO.getMaxNumRows(), statistics.getNumOfRows2numOfTables().get(tableVO.getMaxNumRows())+1);

            for(ColumnVO columnVO : tableVO.getColumns().values()) {
                int columnDistinctTokens = this.countDistinctTokens(columnVO);
                statistics.getNumOfDistinctValues2numberOfColumns().putIfAbsent(columnDistinctTokens,0);
                statistics.getNumOfDistinctValues2numberOfColumns().put(columnDistinctTokens, statistics.getNumOfDistinctValues2numberOfColumns().get(columnDistinctTokens)+1);
            }
        }

        StatsWriter.writeStatistics(statistics, beginTime);

        return statistics;
    }

    private Long getNumberOfEmptyCells(TableVO tableVO) {
        Long numberOfEmptyCells = 0L;

        for(Map.Entry<Integer, ColumnVO> columnVOEntry : tableVO.getColumns().entrySet()) {
            for(Map.Entry<Integer, CellVO> cellVOEntry : columnVOEntry.getValue().getCells().entrySet()) {
                if(isEmpty(cellVOEntry.getValue()))
                    numberOfEmptyCells += 1;
            }
        }

        return numberOfEmptyCells;
    }

    private boolean isEmpty(CellVO cellVO) {
        return cellVO.getContent().equals("");
    }

    private Integer countDistinctTokens(ColumnVO columnVO) {
        Set<String> columnTokens = new HashSet<>();

        for(CellVO cellVO : columnVO.getCells().values()) {
            columnTokens.addAll(List.of(splitToTokens(cellVO.getContent())));
        }

        return columnTokens.size();
    }

    private String[] splitToTokens(String text) {
        return text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }

}
