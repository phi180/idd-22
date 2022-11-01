package it.uniroma3.idd.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class ColumnVO {

    private String header;

    /** each cell is identified by its row number in the column */
    private Map<Integer,CellVO> cells = new TreeMap<>();

    public CellVO insertCell(int rowNumber, String cellContent) {
        return this.cells.put(rowNumber, new CellVO(cellContent));
    }

}
