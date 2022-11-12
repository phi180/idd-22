package it.uniroma3.idd.hw3.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.uniroma3.idd.hw4.api.ParseApi;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.hw3.domain.in.mentor.MentorCell;
import it.uniroma3.idd.hw3.domain.in.mentor.MentorTable;

public class ParseApiImpl implements ParseApi {

    @Override
    public TableVO parse(String inputJsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        MentorTable mentorTable = gson.fromJson(inputJsonText, MentorTable.class);

        if(mentorTable==null)
            return null;

        return toTableVO(mentorTable, false);
    }

    @Override
    public TableVO parseRaw(String inputJsonText) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        MentorTable mentorTable = gson.fromJson(inputJsonText, MentorTable.class);

        if(mentorTable==null)
            return null;

        return toTableVO(mentorTable, true);
    }

    /* ============= PRIVATE METHODS ============= */

    private TableVO toTableVO(MentorTable mentorTable, boolean raw) {
        TableVO tableVO = new TableVO();

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

    private CellVO toCellVO(MentorCell mentorCell, boolean raw) {
        CellVO cellVO = new CellVO();

        cellVO.setContent(raw?mentorCell.getInnerHTML(): mentorCell.getCleanedText());
        return cellVO;
    }


}
