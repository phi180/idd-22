package it.uniroma3.idd.domain.lucene;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

@Data
public class LuceneTable {

    private String oid;

    private Map<Integer, LuceneTableColumn> luceneTableColumns = new TreeMap<>();

}
