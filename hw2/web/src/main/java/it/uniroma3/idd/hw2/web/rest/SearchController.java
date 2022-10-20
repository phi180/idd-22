package it.uniroma3.idd.hw2.web.rest;

import it.uniroma3.idd.hw2.web.domain.dto.ResultEntryDTO;
import it.uniroma3.idd.hw2.web.domain.dto.ResultsDTO;
import it.uniroma3.idd.hw2.web.domain.page.in.SearchInput;
import it.uniroma3.idd.hw2.web.domain.page.out.SearchResultOutput;
import it.uniroma3.idd.hw2.web.domain.page.out.SearchResultsOutput;
import it.uniroma3.idd.hw2.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class SearchController {

    private static final Logger logger = Logger.getLogger(SearchController.class.toString());

    @Autowired
    private SearchService searchService;

    @RequestMapping(value="/search",method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("queryForm", new SearchInput());
        return "search";
    }

    @RequestMapping(value="/search/results",method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("queryForm") SearchInput searchInput) {
        logger.info("SearchController - search(): searchInput="+searchInput.toString());

        ResultsDTO resultsDTO = null;
        if(searchInput.getAdvancedSearch()) {
            resultsDTO = searchService.getResultsParser(searchInput.getSearchField());
        } else {
            resultsDTO = searchService.getResults(searchInput.getSearchField());
        }

        SearchResultsOutput searchResultsOutput = new SearchResultsOutput();
        resultsDTO.getResultListDTO().forEach(
                (resultEntryDTO -> searchResultsOutput.getResults().add(toSearchResultOutput(resultEntryDTO)))
        );

        model.addAttribute("results", searchResultsOutput.getResults());
        return "results";
    }

    /** Converters */
    private SearchResultOutput toSearchResultOutput(ResultEntryDTO resultEntryDTO) {
        SearchResultOutput searchResultOutput = new SearchResultOutput();
        searchResultOutput.setTitle(resultEntryDTO.getFileName());
        searchResultOutput.setDocId(resultEntryDTO.getDocId());

        return searchResultOutput;
    }

}
