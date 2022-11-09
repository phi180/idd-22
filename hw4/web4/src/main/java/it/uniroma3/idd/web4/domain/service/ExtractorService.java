package it.uniroma3.idd.web4.domain.service;

import it.uniroma3.idd.hw4.api.ExtractorApi;
import it.uniroma3.idd.hw4.vo.ExtractedDataVO;
import it.uniroma3.idd.hw4.vo.ExtractedLabeledDataVO;
import it.uniroma3.idd.web4.dto.ExtractedDataDTO;
import it.uniroma3.idd.web4.dto.ExtractedLabeledDataDTO;
import it.uniroma3.idd.web4.rest.ExtractorController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExtractorService {

    private static final Logger logger = LogManager.getLogger(ExtractorService.class);

    @Autowired
    private ExtractorApi extractorApi;

    public ExtractedLabeledDataDTO getDataFromXpath(String url, Map<String,List<String>> label2xpaths) {
        logger.info("ExtractedDataDTO - getDataFromXpath(): url={}, xpath={}",url,label2xpaths);

        return toExtractedLabeledDataDTO(extractorApi.getLabeledDataFromXpath(url,label2xpaths));
    }

    /** private methods */

    private ExtractedLabeledDataDTO toExtractedLabeledDataDTO(ExtractedLabeledDataVO extractedDataVO) {
        ExtractedLabeledDataDTO extractedLabeledDataDTO = new ExtractedLabeledDataDTO();
        extractedLabeledDataDTO.setUrl(extractedDataVO.getUrl());
        extractedLabeledDataDTO.setLabel2xpathdata(extractedDataVO.getLabel2xpathData());
        return extractedLabeledDataDTO;
    }

}
