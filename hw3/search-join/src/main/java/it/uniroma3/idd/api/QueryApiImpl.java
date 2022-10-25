package it.uniroma3.idd.api;

import it.uniroma3.idd.domain.lucene.LuceneTableCell;
import it.uniroma3.idd.domain.lucene.LuceneTableColumn;
import it.uniroma3.idd.domain.result.ResultColumn;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.logic.QueryLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class QueryApiImpl implements QueryApi {

    @Override
    public List<ResultVO> query(List<String[]> tokens, int k) {
        QueryLogic queryLogic = new QueryLogic();

        List<ResultColumn> results = queryLogic.query(tokens, k);
        List<ResultVO> resultColumns = new ArrayList<>();

        for(ResultColumn result : results) {
            resultColumns.add(toResultVO(result));
        }

        return resultColumns;
    }

    @Override
    public List<ResultVO> query(ColumnVO columnVO, int k) {
        List<String[]> groupsOfTokens = new ArrayList<>();
        for(CellVO cellVO : columnVO.getCells().values()) {
            String[] tokens = this.splitToTokens(cellVO.getContent());
            groupsOfTokens.add(tokens);
        }

        return query(groupsOfTokens, k);
    }

    @Override
    public List<ResultVO> query(TableVO tableVO, int k) {
        List<ResultVO> results = new ArrayList<>();
        for(ColumnVO columnVO : tableVO.getColumns().values()) {
            results.addAll(query(columnVO,k));
        }

        return results;
    }

    @Override
    public boolean existsColumn(String tableId, Long columnNum) {
        return new QueryLogic().existsColumnInIndex(tableId,columnNum);
    }

    /** PRIVATE METHODS */

    private String[] splitToTokens(String cellContent) {
        return cellContent.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }

    private ColumnVO toColumnVO(LuceneTableColumn luceneTableColumn) {
        ColumnVO columnVO = new ColumnVO();
        for(Map.Entry<Integer,LuceneTableCell> cellEntry : luceneTableColumn.getCells().entrySet()) {
            columnVO.getCells().put(cellEntry.getKey(), toCellVO(cellEntry.getValue()));
        }

        return columnVO;
    }

    private CellVO toCellVO(LuceneTableCell luceneTableCell) {
        CellVO cellVO = new CellVO();
        cellVO.setContent(luceneTableCell.getContent());
        return cellVO;
    }

    private ResultVO toResultVO(ResultColumn resultColumn) {
        ResultVO resultVO = new ResultVO();
        resultVO.setTableId(resultColumn.getTableId());
        resultVO.setColumnNum(resultColumn.getColumnNum());
        return resultVO;
    }
}
