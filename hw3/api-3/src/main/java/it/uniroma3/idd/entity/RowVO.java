package it.uniroma3.idd.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class RowVO {

    private Map<Integer,CellVO> cells = new TreeMap<>();

    public CellVO insertCell(int colNumber, String cellContent) {
        return this.cells.put(colNumber, new CellVO(cellContent));
    }

}
