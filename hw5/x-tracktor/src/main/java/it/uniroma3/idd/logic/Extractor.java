package it.uniroma3.idd.logic;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.domain.ExtractedTable;

import java.util.List;
import java.util.Map;

public interface Extractor {

    ExtractedLabeledData extractLabeledData(String url, Map<String, List<String>> label2xpath);

    ExtractedTable getTableFromPage(String url, String tableXPath);

}
