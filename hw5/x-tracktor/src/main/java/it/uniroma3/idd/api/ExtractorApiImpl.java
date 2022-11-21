package it.uniroma3.idd.api;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.logic.ExtractorLogic;
import it.uniroma3.idd.utils.csv.CSVUtils;
import it.uniroma3.idd.vo.ExtractedLabeledDataVO;

import java.util.List;
import java.util.Map;

public class ExtractorApiImpl implements ExtractorApi {

    private ExtractorLogic extractorLogic = new ExtractorLogic();

    @Override
    public ExtractedLabeledDataVO getLabeledDataFromXpath(String url, Map<String, List<String>> label2xpaths) {
        //ExtractorLogic extractorLogic = new ExtractorLogic();
        ExtractedLabeledData extractedData = extractorLogic.extractLabeledData(url, label2xpaths);

        ExtractedLabeledDataVO extractedLabeledDataVO = toExtractedLabeledDataVO(extractedData);
        CSVUtils.appendToDataset(extractedLabeledDataVO);

        return extractedLabeledDataVO;
    }

    /** PRIVATE METHODS */

    private ExtractedLabeledDataVO toExtractedLabeledDataVO(ExtractedLabeledData extractedLabeledData) {
        ExtractedLabeledDataVO extractedLabeledDataVO = new ExtractedLabeledDataVO();
        extractedLabeledDataVO.setUrl(extractedLabeledData.getUrl());
        extractedLabeledDataVO.setLabel2xpathData(extractedLabeledData.getLabel2xpathData());
        return extractedLabeledDataVO;
    }

}
