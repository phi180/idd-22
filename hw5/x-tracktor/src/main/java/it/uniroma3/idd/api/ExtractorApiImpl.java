package it.uniroma3.idd.api;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.domain.ExtractedRow;
import it.uniroma3.idd.domain.ExtractedTable;
import it.uniroma3.idd.logic.Extractor;
import it.uniroma3.idd.logic.enums.Technology;
import it.uniroma3.idd.logic.impl.jsoup.ExtractorLogicJsoup;
import it.uniroma3.idd.logic.impl.selenium.ExtractorLogicSelenium;
import it.uniroma3.idd.utils.Constants;
import it.uniroma3.idd.utils.PropertiesReader;
import it.uniroma3.idd.utils.csv.CSVUtils;
import it.uniroma3.idd.vo.ExtractedLabeledDataVO;
import it.uniroma3.idd.vo.RowVO;
import it.uniroma3.idd.vo.TableVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorApiImpl implements ExtractorApi {

    private final Technology TECHNOLOGY = Technology.fromString(PropertiesReader.getProperty(Constants.ENGINE_PROP));

    @Override
    public ExtractedLabeledDataVO getLabeledDataFromSelector(String url, Map<String, List<String>> label2selectors) {
        Extractor extractor = null;

        if(TECHNOLOGY.equals(Technology.SELENIUM)) {
            extractor = new ExtractorLogicSelenium(label2selectors.get("click"));
        } else if(TECHNOLOGY.equals(Technology.JSOUP)) {
            extractor = new ExtractorLogicJsoup();
        }

        ExtractedLabeledData extractedData = extractor.extractLabeledData(url, label2selectors);

        ExtractedLabeledDataVO extractedLabeledDataVO = toExtractedLabeledDataVO(extractedData);
        CSVUtils.appendToDataset(extractedLabeledDataVO);

        return extractedLabeledDataVO;
    }

    @Override
    public TableVO getTableFromPage(String url, Map<String, List<String>> label2selectors) {
        final String TABLE_KEY = "table";

        Extractor extractor = null;

        if(TECHNOLOGY.equals(Technology.SELENIUM)) {
            extractor = new ExtractorLogicSelenium(label2selectors.get("click"));
        } else if(TECHNOLOGY.equals(Technology.JSOUP)) {
            extractor = new ExtractorLogicJsoup();
        }

        ExtractedTable extractedTable = extractor.getTableFromPage(url,label2selectors.get(TABLE_KEY).get(0));

        TableVO tableVO = toTableVO(extractedTable);
        CSVUtils.appendToDataset(tableVO);

        return tableVO;
    }

    /** PRIVATE METHODS */

    private ExtractedLabeledDataVO toExtractedLabeledDataVO(ExtractedLabeledData extractedLabeledData) {
        ExtractedLabeledDataVO extractedLabeledDataVO = new ExtractedLabeledDataVO();
        extractedLabeledDataVO.setUrl(extractedLabeledData.getUrl());
        extractedLabeledDataVO.setLabel2xpathData(extractedLabeledData.getLabel2xpathData());
        return extractedLabeledDataVO;
    }

    private TableVO toTableVO(ExtractedTable extractedTable) {
        TableVO tableVO = new TableVO();
        tableVO.setHeaderRow(toRowVO(extractedTable.getHeaderRow()));

        extractedTable.getRows().forEach(extractedRow -> tableVO.getRows().add(toRowVO(extractedRow)));
        return tableVO;
    }

    private RowVO toRowVO(ExtractedRow extractedRow) {
        RowVO rowVO = new RowVO();
        rowVO.setCells(new ArrayList<>(extractedRow.getCells()));
        return rowVO;
    }

}
