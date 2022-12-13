package it.uniroma3.idd.web3.domain.service;

import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.entity.TableByRowVO;
import it.uniroma3.idd.hw3.api.IndexApi;
import it.uniroma3.idd.hw3.api.QueryApi;
import it.uniroma3.idd.hw3.api.RepoApi;
import it.uniroma3.idd.hw3.token.CustomTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class TableService {

    @Autowired
    private RepoApi repoApi;
    @Autowired
    private IndexApi indexApi;
    @Autowired
    private QueryApi queryApi;

    public TableByRowVO getTable(String oid) {
        log.info("TableService - getTable(): oid={}",oid);

        return repoApi.getTableByOid(oid);
    }

    public void insertDataset(String datasetPath) {
        log.info("TableService - insertDataset(): datasetPath={}",datasetPath);

        repoApi.insertDataset(datasetPath);
        indexApi.createIndex(datasetPath);
    }

    public void insertMultipleDatasets(List<String> datasetsPath) {
        log.info("TableService - insertDataset(): datasetsPath={}",datasetsPath);

        for(String datasetPath : datasetsPath) {
            repoApi.insertDataset(datasetPath);
            indexApi.createIndex(datasetPath);
        }
    }

    public void deleteAll() {
        indexApi.dropIndex();
    }

    public List<TableByRowVO> query(List<String> tokens,int k) {
        List<TableByRowVO> tablesResults = new ArrayList<>();

        List<String[]> cellsWithTokens = new ArrayList<>();
        for(String token : tokens) {
            cellsWithTokens.add(CustomTokenizer.tokenize(token));
        }

        List<ResultVO> results = queryApi.query(cellsWithTokens, k);

        for(ResultVO result : results) {
            tablesResults.add(this.getTable(result.getTableId()));
        }

        return tablesResults;
    }

}
