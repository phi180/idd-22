package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.ColumnarTableVO;
import it.uniroma3.idd.entity.SimpleTableVO;
import it.uniroma3.idd.entity.TableByRowVO;

public interface ParseApi {

    /**
     * @param inputJsonText raw json entry from dataset
     * @return a structured table associated to the input, output is cleaned
     * */
    ColumnarTableVO parseByColumn(String inputJsonText);

    TableByRowVO parseByRow(String inputJsonText);

    SimpleTableVO parseSimple(String inputJsonText);

    TableByRowVO parseSimpleCells(String cellsJson);

    /**
     * @param inputJsonText raw json entry from dataset
     * @return a structured table associated to the input, not filtering html code
     * */
    ColumnarTableVO parseRaw(String inputJsonText);

}
