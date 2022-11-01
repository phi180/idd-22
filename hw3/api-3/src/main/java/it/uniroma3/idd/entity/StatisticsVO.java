package it.uniroma3.idd.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class StatisticsVO {

    private Long numberOfTables;
    private Long totalNumberOfRows;
    private Long totalNumberOfColumns;
    private Long numberOfEmptyCells;

    private Map<Integer,Integer> numOfRows2numOfTables = new TreeMap<>();
    private Map<Integer,Integer> numOfColumns2numOfTables = new TreeMap<>();
    private Map<Integer,Integer> numOfDistinctValues2numberOfColumns = new TreeMap<>();

    private Double meanNumberOfColumnsForTable;
    private Double meanNumberOfRowsForTable;
    private Double meanNumberOfEmptyCells;
    private Double meanDistinctElementsInColumns;
    private Double standardDevDistinctElements2Columns;

}
