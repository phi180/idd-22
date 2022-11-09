package it.uniroma3.idd.web4.domain.service;

import it.uniroma3.idd.web4.domain.model.Rule;
import it.uniroma3.idd.web4.domain.model.XPathExpressionsFamily;
import it.uniroma3.idd.web4.domain.repository.RuleRepository;
import it.uniroma3.idd.web4.dto.in.AddRuleXPathDTO;
import it.uniroma3.idd.web4.dto.in.RuleInDTO;
import it.uniroma3.idd.web4.dto.out.RuleItemOutDTO;
import it.uniroma3.idd.web4.dto.out.RuleOutDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RuleService {

    private static final Logger logger = LogManager.getLogger(RuleService.class);

    @Autowired
    private RuleRepository ruleRepository;

    @Transactional
    public RuleOutDTO addRule(RuleInDTO ruleInDTO) {
        logger.info("RuleService - addRule(): ruleInDTO={}",ruleInDTO);
        Rule rule = new Rule();
        rule.setName(ruleInDTO.getName());
        rule.setDescription(rule.getDescription());
        rule = this.ruleRepository.save(rule);

        return toRuleOutDTO(rule);
    }

    @Transactional
    public RuleOutDTO addXpathInRule(Long ruleId, AddRuleXPathDTO addRuleXPathDTO) {
        logger.info("RuleService - addXpathInRule(): ruleId={}, addRuleXPathDTO={}",ruleId,addRuleXPathDTO);
        Rule rule = this.ruleRepository.findById(ruleId).get();
        rule.getLabel2xpathExpressions().putIfAbsent(addRuleXPathDTO.getLabel(), new XPathExpressionsFamily());
        rule.getLabel2xpathExpressions().get(addRuleXPathDTO.getLabel()).addExpression(addRuleXPathDTO.getXpath());
        rule = ruleRepository.save(rule);

        return toRuleOutDTO(rule);
    }

    public List<RuleOutDTO> getAllRules() {
        logger.info("RuleService - getAllRules()");
        List<RuleOutDTO> rules = new ArrayList<>();
        this.ruleRepository.findAll().forEach(rule -> rules.add(toRuleOutDTO(rule)));

        return rules;
    }

    public RuleOutDTO getRuleById(Long ruleId) {
        return this.toRuleOutDTO(this.ruleRepository.findById(ruleId).get());
    }

    @Transactional
    public List<RuleItemOutDTO> getAllRulesItems() {
        logger.info("RuleService - getAllRulesItems()");
        List<RuleItemOutDTO> rules = new ArrayList<>();
        this.ruleRepository.findAll().forEach(rule -> rules.add(toRuleItemOutDTO(rule)));

        return rules;
    }

    /** private methods */

    private RuleOutDTO toRuleOutDTO(Rule rule) {
        RuleOutDTO ruleOutDTO = new RuleOutDTO();
        ruleOutDTO.setId(rule.getId());
        ruleOutDTO.setDescription(rule.getDescription());
        for(Map.Entry<String,XPathExpressionsFamily> entry : rule.getLabel2xpathExpressions().entrySet()) {
            String label = entry.getKey();
            List<String> xpaths = entry.getValue().getExpressions();

            ruleOutDTO.getLabel2xpathExpressions().put(label,xpaths);
        }

        return ruleOutDTO;
    }

    private RuleItemOutDTO toRuleItemOutDTO(Rule rule) {
        RuleItemOutDTO ruleItemOutDTO = new RuleItemOutDTO();
        ruleItemOutDTO.setId(rule.getId());
        ruleItemOutDTO.setName((ruleItemOutDTO.getName()));
        ruleItemOutDTO.setDescription(rule.getDescription());

        return ruleItemOutDTO;
    }

}
