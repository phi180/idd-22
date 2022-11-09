package it.uniroma3.idd.web4.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtractedDataDTO {
    private String url;

    private Map<String, String> xpath2data = new HashMap<>();

}
