package it.uniroma3.idd.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class TableByRowVO {

    private String oid;
    private Map<Integer,RowVO> rows = new TreeMap<>();

    private int maxNumRows;

    private int maxNumColumns;

    public void insertCell(int columnIndex, int rowNumber, CellVO cellVO) {
        this.rows.putIfAbsent(rowNumber,new RowVO());
        this.rows.get(rowNumber).insertCell(columnIndex,cellVO.getContent());
    }

}
