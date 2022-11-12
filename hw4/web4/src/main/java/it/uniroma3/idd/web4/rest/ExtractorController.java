package it.uniroma3.idd.web4.rest;

import it.uniroma3.idd.web4.domain.model.Rule;
import it.uniroma3.idd.web4.domain.service.ExtractorService;
import it.uniroma3.idd.web4.domain.service.RuleService;
import it.uniroma3.idd.web4.dto.ExtractedLabeledDataDTO;
import it.uniroma3.idd.web4.dto.in.ExtractInputDTO;
import it.uniroma3.idd.web4.dto.out.RuleItemOutDTO;
import it.uniroma3.idd.web4.dto.out.RuleOutDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ExtractorController {

    private static final Logger logger = LogManager.getLogger(ExtractorController.class);

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(Model model) {
        logger.info("ExtractorController - home()");
        List<RuleItemOutDTO> allRules = this.ruleService.getAllRulesItems();
        model.addAttribute("searchForm", new ExtractInputDTO());
        model.addAttribute("rules", allRules);

        return "home";
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public String extractData(Model model,@ModelAttribute("extractForm") ExtractInputDTO extractInputDTO) {
        logger.info("ExtractorController - extractData(): extractInputDTO={}",extractInputDTO);
        RuleOutDTO rule = this.ruleService.getRuleById(extractInputDTO.getRuleId());
        ExtractedLabeledDataDTO extractedLabeledDataDTO = this.extractorService.getDataFromXpath(extractInputDTO.getUrl(), rule.getLabel2xpathExpressions());

        model.addAttribute("data", extractedLabeledDataDTO);

        return "result";
    }

}
