package it.uniroma3.idd.web3.rest;

import lombok.Data;

import java.util.List;

@Data
public class QueryRequest {

    private List<String> columnCells;

    private int k;

}
