package it.uniroma3.idd.hw2.web.rest;

import it.uniroma3.idd.hw2.web.domain.page.in.BuildIndexInput;
import it.uniroma3.idd.hw2.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class IndexController {

    private final Logger logger = Logger.getLogger(IndexController.class.toString());

    @Autowired
    private IndexService indexService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(Model model) {
        logger.info("IndexController - index()");
        model.addAttribute("indexInput", new BuildIndexInput());
        return "index";
    }

    @RequestMapping(value="/index/build",method = RequestMethod.POST)
    public String buildIndex(@ModelAttribute("indexForm") BuildIndexInput buildIndexInput, Model model) {
        logger.info("IndexController - buildIndex(): dirPath=" + buildIndexInput.getDirPath());
        indexService.buildIndex(buildIndexInput.getDirPath());
        return "redirect:/index";
    }

}
