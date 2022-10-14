package it.uniroma3.idd.hw2.api;

import it.uniroma3.idd.hw2.dto.PaginatedResultsDTO;
import it.uniroma3.idd.hw2.dto.ResultsDTO;

public interface SearchApi {

    ResultsDTO getAllResults();

    PaginatedResultsDTO getPaginatedResults(int pageNumber, int pageSize);

}
