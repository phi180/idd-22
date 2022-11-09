package it.uniroma3.idd.hw4.api;

import it.uniroma3.idd.hw4.logic.IndexCreationLogic;

import java.io.IOException;

public class IndexApiImpl implements IndexApi {

    private IndexCreationLogic indexCreationLogic;

    public IndexApiImpl() {
        this.indexCreationLogic = new IndexCreationLogic();
    }

    @Override
    public void createIndex(String datasetPath) {
        try {
            indexCreationLogic.createIndex(datasetPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
