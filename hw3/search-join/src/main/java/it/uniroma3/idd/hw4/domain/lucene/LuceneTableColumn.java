package it.uniroma3.idd.hw4.domain.lucene;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class LuceneTableColumn {

    private Map<Integer, LuceneTableCell> cells = new TreeMap();
}
