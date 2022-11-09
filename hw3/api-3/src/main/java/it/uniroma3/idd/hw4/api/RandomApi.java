package it.uniroma3.idd.hw4.api;

import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.TableVO;

public interface RandomApi {

    TableVO getRandomTable(String datasetPath);

    ColumnVO getRandomColumn(String datasetPath);

}