package it.uniroma3.idd.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableVO {

    private RowVO headerRow;

    private List<RowVO> rows = new ArrayList<>();

}
