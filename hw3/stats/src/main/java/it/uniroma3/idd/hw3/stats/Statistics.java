package it.uniroma3.idd.hw3.stats;

import it.uniroma3.idd.hw3.util.Utils;
import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class Statistics {

    /** Numero di tabelle */
    private Long numberOfTables = 0L;

    /** Numero medio di righe */
    private Long totalNumberOfRows = 0L;

    private Long totalNumberOfColumns = 0L;

    private Long numberOfEmptyCells = 0L;

    /** Distribuzione numero di righe (quante tabelle hanno 1, 2, 3, 4, etc. righe) */
    private Map<Integer,Integer> numOfRows2numOfTables = new TreeMap<>();

    /** Distribuzione numero di colonne (quante tabelle hanno 1, 2, 3, 4, etc. colonne) */
    private Map<Integer,Integer> numOfColumns2numOfTables = new TreeMap<>();

    /** Distribuzione valori distinti (quante colonne hanno 1, 2, 3, 4, etc valori distinti) */
    private Map<Integer,Integer> numOfDistinctValues2numberOfColumns = new TreeMap<>();

    /** Numero medio di colonne */
    public Double getMeanNumberOfColumnsForTable() {
        return totalNumberOfColumns.doubleValue() / numberOfTables;
    }

    /** Numero medio di righe */
    public Double getMeanNumberOfRowsForTable() {
        return totalNumberOfRows.doubleValue() / numberOfTables;
    }

    /** Numero medio di valori nulli per tabella */
    public Double getMeanNumberOfEmptyCells() {
        return numberOfEmptyCells.doubleValue() / numberOfTables;
    }

    public Double getMeanDistinctElementsInColumn() {
        return Utils.getMean(numOfDistinctValues2numberOfColumns);
    }

    public Double getStandardDevDistinctElements2Columns() {
        return Utils.getStandardDeviation(numOfDistinctValues2numberOfColumns);
    }

    public Double getStandardDevRows2Tables() {
        return Utils.getStandardDeviation(numOfRows2numOfTables);
    }

    /** increase methods */

    public void increaseNumberOfTables() {
        this.numberOfTables += 1L;
    }

    public void increaseTotalNumberOfRows(int quantity) {
        this.totalNumberOfRows += quantity;
    }

    public void increaseTotalNumberOfColumns(int quantity) {
        this.totalNumberOfColumns += quantity;
    }

    public void increaseQuantityEmptyCells(Long quantity) {
        this.numberOfEmptyCells += quantity;
    }

}
