package it.uniroma3.idd.hw2.web.rest;

import it.uniroma3.idd.hw2.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @PostMapping("/index/build")
    public String buildIndex(@ModelAttribute("indexForm") String dirPath, Model model) {
        indexService.buildIndex(dirPath);
        return "build";
    }

}
