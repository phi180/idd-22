package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.entity.TableVO;

import java.util.List;

public interface QueryApi {

    /**
     * @param tokens a list of string arrays representing single tokens in a cell
     * @param k needed for top-k selection
     * @return a set of results, each one representing the column in a table
     * */
    List<ResultVO> query(List<String[]> tokens, int k);

    /**
     * @param columnVO a column taken from a table
     * @param k needed for top-k selection
     * @return a set of results, each one representing the column in a table
     * */
    List<ResultVO> query(ColumnVO columnVO, int k);

    /**
     * @param tableVO a table where each column will be used for table join search
     * @param k needed for top-k selection
     * @return a set of results, each one representing the column in a table
     * */
    List<ResultVO> query(TableVO tableVO, int k);

}
