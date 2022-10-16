package it.uniroma3.idd.hw2.web.service;

import it.uniroma3.idd.hw2.api.SearchApi;
import it.uniroma3.idd.hw2.api.SearchApiImpl;
import it.uniroma3.idd.hw2.web.domain.dto.ResultEntryDTO;
import it.uniroma3.idd.hw2.web.domain.dto.ResultsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private SearchApi searchApi = new SearchApiImpl();

    public ResultsDTO getResults(String query) {
        return toResultsDTO(searchApi.getAllResults(query));
    }

    public ResultsDTO getResultsParser(String queryWithParser) {
        return toResultsDTO(searchApi.getAllResultsWithParser(queryWithParser));
    }

    private ResultsDTO toResultsDTO(it.uniroma3.idd.hw2.dto.ResultsDTO apiResultsDTO) {
        ResultsDTO resultsDTO = new ResultsDTO();
        apiResultsDTO.getResultListDTO().forEach((entryDTO) -> resultsDTO.getResultListDTO().add(toResultEntryDTO(entryDTO)));
        return resultsDTO;
    }

    private ResultEntryDTO toResultEntryDTO(it.uniroma3.idd.hw2.dto.ResultEntryDTO apiResultEntryDTO) {
        ResultEntryDTO resultEntryDTO = new ResultEntryDTO();
        resultEntryDTO.setFileName(apiResultEntryDTO.getFileName());
        resultEntryDTO.setDocId(apiResultEntryDTO.getDocId());

        return resultEntryDTO;
    }

}
