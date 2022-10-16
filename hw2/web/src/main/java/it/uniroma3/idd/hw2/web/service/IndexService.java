package it.uniroma3.idd.hw2.web.service;

import it.uniroma3.idd.hw2.api.IndexBuildApi;
import it.uniroma3.idd.hw2.api.IndexBuildApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
    private IndexBuildApi indexBuildApi = new IndexBuildApiImpl();

    public void buildIndex(String dirPath) {
        indexBuildApi.buildIndex(dirPath);
    }

}
