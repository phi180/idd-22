package it.uniroma3.idd.api;

import it.uniroma3.idd.vo.ExtractedDataVO;

import java.util.List;

public interface ExtractorApi {

    ExtractedDataVO getDataFromXpath(String url, List<String> xpaths);

}
