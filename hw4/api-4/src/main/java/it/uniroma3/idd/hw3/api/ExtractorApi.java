package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.hw3.vo.ExtractedDataVO;
import it.uniroma3.idd.hw3.vo.ExtractedLabeledDataVO;

import java.util.List;
import java.util.Map;

public interface ExtractorApi {

    ExtractedDataVO getDataFromXpath(String url, List<String> xpaths);

    ExtractedLabeledDataVO getLabeledDataFromXpath(String url, Map<String,List<String>> label2xpaths);


}
