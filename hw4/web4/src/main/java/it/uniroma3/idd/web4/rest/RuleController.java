package it.uniroma3.idd.web4.rest;

import it.uniroma3.idd.web4.domain.service.RuleService;
import it.uniroma3.idd.web4.dto.in.AddRuleXPathDTO;
import it.uniroma3.idd.web4.dto.in.EditRuleInDTO;
import it.uniroma3.idd.web4.dto.in.RuleInDTO;
import it.uniroma3.idd.web4.dto.out.RuleOutDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/rule")
public class RuleController {

    private static final Logger logger = LogManager.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String showAllRules(Model model) {
        logger.info("RuleController - showAllRules()");
        List<RuleOutDTO> rules = this.ruleService.getAllRules();
        model.addAttribute("rules", rules);

        return "rules";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showNewRuleForm(Model model) {
        logger.info("RuleController - showNewRuleForm()");
        model.addAttribute("ruleForm", new RuleInDTO());

        return "newRule";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addRule(Model model, @ModelAttribute("ruleForm") RuleInDTO ruleInDTO) {
        logger.info("RuleController - addRule(): ruleInDTO={}",ruleInDTO);
        RuleOutDTO ruleOutDTO = this.ruleService.addRule(ruleInDTO);

        return "redirect:/rule/"+ruleOutDTO.getId();
    }

    @RequestMapping(value = "/{ruleId}", method = RequestMethod.GET)
    public String showRuleDetails(Model model, @PathVariable("ruleId") Long ruleId) {
        logger.info("RuleController - showRuleDetails()");
        RuleOutDTO ruleOutDTO = this.ruleService.getRuleById(ruleId);
        model.addAttribute("rule", ruleOutDTO);
        model.addAttribute("xpathRuleForm", new AddRuleXPathDTO());

        return "rule";
    }

    @RequestMapping(value = "/{ruleId}/edit", method = RequestMethod.GET)
    public String showEditRule(Model model, @PathVariable("ruleId") Long ruleId) {
        logger.info("RuleController - editRule()");
        RuleOutDTO ruleOutDTO = this.ruleService.getRuleById(ruleId);
        model.addAttribute("rule", ruleOutDTO);

        return "editRule";
    }

    @RequestMapping(value = "/{ruleId}/edit", method = RequestMethod.POST)
    public String editRule(Model model,@PathParam("ruleId") Long ruleId, @RequestBody EditRuleInDTO editRuleInDTO) {
        logger.info("RuleController - editRule(): editRuleInDTO={}",editRuleInDTO);

        return "editRule";
    }

    @RequestMapping(value = "/{ruleId}/xpath/add", method = RequestMethod.POST)
    public String addXpathToRule(Model model, @PathVariable("ruleId") Long ruleId, @ModelAttribute("addRuleXPathForm") AddRuleXPathDTO addRuleXPathDTO) {
        logger.info("RuleController - addXpathToRule()");
        this.ruleService.addXpathInRule(ruleId,addRuleXPathDTO);

        return "redirect:/rule/" + ruleId;
    }

    @RequestMapping(value = "/{ruleId}/label/{label}/remove", method = RequestMethod.GET)
    public String deleteLabelInRule(Model model, @PathVariable("ruleId") Long ruleId, @PathVariable("label") String label) {
        logger.info("RuleController - deleteLabelInRule(): ruleId={}, label={}",ruleId,label);
        this.ruleService.deleteLabelInRule(ruleId,label);

        return "redirect:/rule/" + ruleId;
    }


}
