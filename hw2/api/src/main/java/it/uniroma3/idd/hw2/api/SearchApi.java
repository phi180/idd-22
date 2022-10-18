package it.uniroma3.idd.hw2.api;

import it.uniroma3.idd.hw2.dto.PaginatedResultsDTO;
import it.uniroma3.idd.hw2.dto.ResultsDTO;

public interface SearchApi {
    ResultsDTO getAllResults(String query);

    ResultsDTO getAllResultsPhrase(String query);

    ResultsDTO getAllResultsWithParser(String query);

    /**
     * @return paginated results returned by index
     * */
    PaginatedResultsDTO getPaginatedResults(String query, int pageNumber, int pageSize);
}
