package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ColumnarTableVO;

public interface RandomApi {

    ColumnarTableVO getRandomTable(String datasetPath);

    ColumnVO getRandomColumn(String datasetPath);

}
