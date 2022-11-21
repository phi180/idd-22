package it.uniroma3.idd.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtractedData {

    private String url;

    private Map<String, String> xpath2data = new HashMap<>();

}
