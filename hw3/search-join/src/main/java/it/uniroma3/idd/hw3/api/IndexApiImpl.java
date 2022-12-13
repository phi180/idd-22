package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.hw3.logic.IndexCreationLogic;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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

    @Override
    public void dropIndex() {
        try {
            indexCreationLogic.dropIndex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
