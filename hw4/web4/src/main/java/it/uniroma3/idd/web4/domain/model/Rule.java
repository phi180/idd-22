package it.uniroma3.idd.web4.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyJoinColumn
    private Map<String, XPathExpressionsFamily> label2xpathExpressions;

    public Rule() {
        this.label2xpathExpressions = new HashMap<>();
    }

}
