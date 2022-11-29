package it.uniroma3.idd.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtractedRow {

    private List<String> cells = new ArrayList<>();

}
