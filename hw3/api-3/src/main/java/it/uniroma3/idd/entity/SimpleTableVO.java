package it.uniroma3.idd.entity;

import lombok.Data;

@Data
public class SimpleTableVO {

    private String oid;

    private String jsonContent;

    private int maxNumRows;

    private int maxNumColumns;

}
