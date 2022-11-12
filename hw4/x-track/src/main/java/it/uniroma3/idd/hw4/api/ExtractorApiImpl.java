package it.uniroma3.idd.hw4.api;

import it.uniroma3.idd.hw4.domain.ExtractedData;
import it.uniroma3.idd.hw4.domain.ExtractedLabeledData;
import it.uniroma3.idd.hw4.logic.ExtractorLogic;
import it.uniroma3.idd.hw4.utils.csv.CSVUtils;
import it.uniroma3.idd.hw4.vo.ExtractedDataVO;
import it.uniroma3.idd.hw4.vo.ExtractedLabeledDataVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExtractorApiImpl implements ExtractorApi {

    @Override
    public ExtractedDataVO getDataFromXpath(String url, List<String> xpaths) {
        ExtractorLogic extractorLogic = new ExtractorLogic();
        ExtractedData extractedData = extractorLogic.extractData(url, xpaths);

        return toExtractedDataVO(extractedData);
    }

    @Override
    public ExtractedLabeledDataVO getLabeledDataFromXpath(String url, Map<String, List<String>> label2xpaths) {
        ExtractorLogic extractorLogic = new ExtractorLogic();
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

    private ExtractedDataVO toExtractedDataVO(ExtractedData extractedData) {
        ExtractedDataVO extractedDataVO = new ExtractedDataVO();
        extractedDataVO.setUrl(extractedData.getUrl());
        extractedDataVO.setXpath2data(extractedData.getXpath2data());
        return extractedDataVO;
    }

}
