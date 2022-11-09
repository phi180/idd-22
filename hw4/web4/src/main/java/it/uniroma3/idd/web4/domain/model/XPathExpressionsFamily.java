package it.uniroma3.idd.web4.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class XPathExpressionsFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @CollectionTable
    @Column
    private List<String> expressions;

    public boolean addExpression(String expression) {
        return this.expressions.add(expression);
    }

}
