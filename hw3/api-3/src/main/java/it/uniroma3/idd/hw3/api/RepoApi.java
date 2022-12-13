package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.TableByRowVO;

public interface RepoApi {

    void insertDataset(String datasetPath);

    TableByRowVO getTableByOid(String oid);

}
