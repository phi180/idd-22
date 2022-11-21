package it.uniroma3.idd.api;

import it.uniroma3.idd.vo.ExtractedLabeledDataVO;

import java.util.List;
import java.util.Map;

public interface ExtractorApi {

    ExtractedLabeledDataVO getLabeledDataFromXpath(String url, Map<String, List<String>> label2xpaths);

}
