package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author My Linh Lu
 * Manage elements for index
 */
@Controller()
public class IndexController {

    @GetMapping({"/", "/login"})
    public String showIndex(Model model,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails != null) {
            model.addAttribute("person", userDetails.getPerson());
        }

        model.addAttribute("pageTitle", "IntraClass™");
        model.addAttribute("activePage", "login");
        return "index";
    }
}