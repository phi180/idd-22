package it.uniroma3.idd.hw2.web.service;

import it.uniroma3.idd.hw2.api.IndexApi;
import it.uniroma3.idd.hw2.api.IndexApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    @Autowired
    private IndexApi indexApi;

    public void buildIndex(String dirPath) {
        indexApi.buildIndex(dirPath);
    }

    public void deleteIndex() {
        indexApi.deleteIndex();
    }

    public void deleteIndexAndStats() {
        indexApi.deleteIndexAndStats();
    }

}
