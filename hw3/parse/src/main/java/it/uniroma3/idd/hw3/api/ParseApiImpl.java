package it.uniroma3.idd.hw3.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnarTableVO;
import it.uniroma3.idd.entity.SimpleTableVO;
import it.uniroma3.idd.entity.TableByRowVO;
import it.uniroma3.idd.hw3.domain.SimpleTable;
import it.uniroma3.idd.hw3.domain.in.mentor.MentorCell;
import it.uniroma3.idd.hw3.domain.in.mentor.MentorTable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Component
public class ParseApiImpl implements ParseApi {

    @Override
    public ColumnarTableVO parseByColumn(String inputJsonText) {
        return toColumnarTableVO(toMentorTable(inputJsonText), false);
    }

    @Override
    public TableByRowVO parseByRow(String inputJsonText) {
        return toTableByRowVO(toMentorTable(inputJsonText), false);
    }

    @Override
    public SimpleTableVO parseSimple(String inputJsonText) {
        SimpleTable simpleTable = new Gson().fromJson(inputJsonText,SimpleTable.class);
        return toSimpleTableVO(simpleTable);
    }

    @Override
    public TableByRowVO parseSimpleCells(String cellsJson) {
        TableByRowVO table = new TableByRowVO();

        Gson gson = new Gson();
        Type cellsListType = new TypeToken<ArrayList<MentorCell>>(){}.getType();
        ArrayList<MentorCell> cellsArray = gson.fromJson(cellsJson,cellsListType);

        for(MentorCell mentorCell : cellsArray) {
            CellVO cellVO = new CellVO();
            cellVO.setContent(mentorCell.getCleanedText());

            table.insertCell(mentorCell.getCoordinates().getColumn(),
                    mentorCell.getCoordinates().getRow(), cellVO);
        }

        return table;
    }

    @Override
    public ColumnarTableVO parseRaw(String inputJsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        MentorTable mentorTable = gson.fromJson(inputJsonText, MentorTable.class);

        if(mentorTable==null)
            return new ColumnarTableVO();

        return toColumnarTableVO(mentorTable, true);
    }

    /* ============= PRIVATE METHODS ============= */

    private MentorTable toMentorTable(String inputJsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        MentorTable mentorTable = gson.fromJson(inputJsonText, MentorTable.class);

        if(mentorTable==null)
            return new MentorTable();

        return mentorTable;
    }

    private ColumnarTableVO toColumnarTableVO(MentorTable mentorTable, boolean raw) {
        ColumnarTableVO tableVO = new ColumnarTableVO();

        for(MentorCell mentorCell : mentorTable.getCells()) {
            if(mentorCell.isHeader())
                tableVO.editColumnName(mentorCell.getCoordinates().getColumn(), raw?mentorCell.getInnerHTML():mentorCell.getCleanedText());
            else
                tableVO.insertCell(mentorCell.getCoordinates().getColumn(), mentorCell.getCoordinates().getRow(), toCellVO(mentorCell, raw));
        }

        tableVO.setOid(mentorTable.get_id().getOid());
        tableVO.setMaxNumRows(mentorTable.getMaxDimensions().getRow());
        tableVO.setMaxNumColumns(mentorTable.getMaxDimensions().getColumn());

        return tableVO;
    }

    private TableByRowVO toTableByRowVO(MentorTable mentorTable, boolean raw) {
        TableByRowVO tableVO = new TableByRowVO();

        for(MentorCell mentorCell : mentorTable.getCells()) {
            tableVO.insertCell(mentorCell.getCoordinates().getColumn(), mentorCell.getCoordinates().getRow(), toCellVO(mentorCell, raw));
        }

        tableVO.setOid(mentorTable.get_id().getOid());
        tableVO.setMaxNumRows(mentorTable.getMaxDimensions().getRow());
        tableVO.setMaxNumColumns(mentorTable.getMaxDimensions().getColumn());

        return tableVO;
    }

    private SimpleTableVO toSimpleTableVO(SimpleTable simpleTable) {
        SimpleTableVO simpleTableVO = new SimpleTableVO();
        simpleTableVO.setOid(simpleTable.get_id().getOid());
        simpleTableVO.setJsonContent(simpleTable.getCells().toString());
        simpleTableVO.setMaxNumColumns(simpleTableVO.getMaxNumColumns());
        simpleTableVO.setMaxNumRows(simpleTableVO.getMaxNumRows());

        return simpleTableVO;
    }

    private CellVO toCellVO(MentorCell mentorCell, boolean raw) {
        CellVO cellVO = new CellVO();

        cellVO.setContent(raw?mentorCell.getInnerHTML(): mentorCell.getCleanedText());
        return cellVO;
    }


}
