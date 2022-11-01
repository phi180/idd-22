package it.uniroma3.idd.domain.lucene;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class LuceneTableColumn {

    private Map<Integer, LuceneTableCell> cells = new TreeMap();
}
