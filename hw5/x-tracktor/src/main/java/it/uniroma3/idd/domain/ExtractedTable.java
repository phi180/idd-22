package it.uniroma3.idd.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtractedTable {

    private ExtractedRow headerRow;

    private List<ExtractedRow> rows = new ArrayList<>();

}
