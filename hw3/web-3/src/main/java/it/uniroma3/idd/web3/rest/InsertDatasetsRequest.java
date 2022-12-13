package it.uniroma3.idd.web3.rest;

import lombok.Data;

import java.util.List;

@Data
public class InsertDatasetsRequest {

    private List<String> datasetPath;

}
