package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author My Linh Lu
 * Manage elements for person page
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping({"", "/"})
    public String personOverview(Model model) {
        model.addAttribute("pageTitle", "Person Homepage");
        return "person";
    }
}
