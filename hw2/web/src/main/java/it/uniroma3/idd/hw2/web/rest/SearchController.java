package it.uniroma3.idd.hw2.web.rest;

import it.uniroma3.idd.hw2.web.domain.dto.ResultsDTO;
import it.uniroma3.idd.hw2.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/search/base/{query}")
    public String search(Model model, @ModelAttribute("query") String queryString) {
        ResultsDTO resultsDTO = searchService.getResults(queryString);
        model.addAttribute("results", resultsDTO);
        return "results";
    }

    @PostMapping("/search/parser/{query}")
    public String searchWithParser(Model model, @ModelAttribute("query") String parserQueryString) {
        ResultsDTO resultsDTO = searchService.getResultsParser(parserQueryString);
        model.addAttribute("results", resultsDTO);
        return "results";
    }

}
