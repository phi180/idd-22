package it.uniroma3.idd.api;

import it.uniroma3.idd.vo.ExtractedLabeledDataVO;
import it.uniroma3.idd.vo.TableVO;

import java.util.List;
import java.util.Map;

public interface ExtractorApi {

    ExtractedLabeledDataVO getLabeledDataFromSelector(String url, Map<String, List<String>> label2selectors);

    TableVO getTableFromPage(String url, Map<String, List<String>> label2selectors);

}
