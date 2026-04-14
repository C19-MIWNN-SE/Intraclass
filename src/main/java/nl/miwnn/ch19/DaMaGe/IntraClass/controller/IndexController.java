package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author My Linh Lu
 * Manage elements for index
 */
@Controller()
public class IndexController {

    @GetMapping({ "/"})
    public String showIndex(Model model) {
        model.addAttribute("pageTitle", "Login - IntraClass");
        return "index";
    }
}