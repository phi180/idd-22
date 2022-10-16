package it.uniroma3.idd.hw2.web.domain.page.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchInput {

    private String searchField;

    private Boolean advancedSearch;

}
