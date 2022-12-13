package it.uniroma3.idd.logic.impl.jsoup;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.domain.ExtractedRow;
import it.uniroma3.idd.domain.ExtractedTable;
import it.uniroma3.idd.logic.Extractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorLogicJsoup implements Extractor {

    public ExtractedLabeledData extractLabeledData(String url, Map<String, List<String>> label2css) {
        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();

        for(Map.Entry<String,List<String>> label2cssEntry : label2css.entrySet()) {
            String label = label2cssEntry.getKey();

            Document htmlDoc = null;
            try {
                htmlDoc = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            extractedLabeledData.getLabel2xpathData().putIfAbsent(label,new HashMap<>());
            for(String css: label2cssEntry.getValue()) {
                try {
                    String result = htmlDoc.select(css).text();
                    extractedLabeledData.getLabel2xpathData().get(label).put(css, result);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return extractedLabeledData;
    }

    public ExtractedTable getTableFromPage(String url, String tableSelector) {
        ExtractedTable extractedTable = new ExtractedTable();

        final String THEAD = "thead";
        final String TBODY = "tbody";
        final String TR = "tr";
        final String TH = "th";
        final String TD = "td";

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements table = document.select(tableSelector);

        ExtractedRow rowHeader = new ExtractedRow();
        Elements headerCells = table.select(THEAD).select(TR).select(TH);
        for(Element headerCell : headerCells) {
            rowHeader.getCells().add(headerCell.text());
        }
        extractedTable.setHeaderRow(rowHeader);

        Elements tableRows = table.select(TBODY).select(TR);
        for(Element tableRow : tableRows) {
            ExtractedRow extractedRow = new ExtractedRow();

            Elements rowCells = tableRow.select(TD);
            for(Element rowCell : rowCells) {
                extractedRow.getCells().add(rowCell.text());
            }

            extractedTable.getRows().add(extractedRow);
        }

        return extractedTable;
    }

}
