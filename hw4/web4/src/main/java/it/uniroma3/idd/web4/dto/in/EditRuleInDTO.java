package it.uniroma3.idd.web4.dto.in;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EditRuleInDTO {

    private String name;

    private String description;

    private Map<String, List<String>> label2xpathExpressions = new HashMap<>();

}
