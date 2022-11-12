package it.uniroma3.idd.web4.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtractedLabeledDataDTO {

    private String url;

    private Map<String, Map<String,String>> label2xpathdata = new HashMap<>();

}
