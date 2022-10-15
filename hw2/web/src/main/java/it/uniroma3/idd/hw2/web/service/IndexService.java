package it.uniroma3.idd.hw2.web.service;

import it.uniroma3.idd.hw2.api.IndexBuildApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    @Autowired
    private IndexBuildApi indexBuildApi;

    public void buildIndex(String dirPath) {
        indexBuildApi.buildIndex(dirPath);
    }

}
