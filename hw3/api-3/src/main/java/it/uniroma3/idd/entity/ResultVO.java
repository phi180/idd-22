package it.uniroma3.idd.entity;

import lombok.Data;

@Data
public class ResultVO {

    private String tableId;
    private Long columnNum;

    @Override
    public String toString() {
        return tableId + ":" + columnNum;
    }

}
