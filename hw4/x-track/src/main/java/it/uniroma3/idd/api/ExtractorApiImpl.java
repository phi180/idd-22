package it.uniroma3.idd.api;

import it.uniroma3.idd.domain.ExtractedData;
import it.uniroma3.idd.logic.ExtractorLogic;
import it.uniroma3.idd.vo.ExtractedDataVO;

import java.util.List;

public class ExtractorApiImpl implements ExtractorApi {

    @Override
    public ExtractedDataVO getDataFromXpath(String url, List<String> xpaths) {
        ExtractorLogic extractorLogic = new ExtractorLogic();
        ExtractedData extractedData = extractorLogic.extractData(url, xpaths);

        return toExtractedDataVO(extractedData);
    }

    /** PRIVATE METHODS */

    private ExtractedDataVO toExtractedDataVO(ExtractedData extractedData) {
        ExtractedDataVO extractedDataVO = new ExtractedDataVO();
        extractedDataVO.setUrl(extractedData.getUrl());
        extractedDataVO.setXpath2data(extractedData.getXpath2data());
        return extractedDataVO;
    }

}
