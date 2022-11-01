package it.uniroma3.idd.entity;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class TableVO {

    private String oid;
    private Map<Integer,ColumnVO> columns = new TreeMap<>();

    private int maxNumRows;

    private int maxNumColumns;

    public void editColumnName(int columnIndex, String columnName) {
        this.columns.putIfAbsent(columnIndex,new ColumnVO());
        this.columns.get(columnIndex).setHeader(columnName);
    }

    public void insertCell(int columnIndex, int rowNumber, CellVO cellVO) {
        this.columns.putIfAbsent(columnIndex,new ColumnVO());
        this.columns.get(columnIndex).insertCell(rowNumber,cellVO.getContent());
    }

}
