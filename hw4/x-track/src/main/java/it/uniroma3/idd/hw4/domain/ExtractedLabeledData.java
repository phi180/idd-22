package it.uniroma3.idd.hw4.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtractedLabeledData {
    private String url;

    private Map<String, Map<String,String>> label2xpathData = new HashMap<>();
}
