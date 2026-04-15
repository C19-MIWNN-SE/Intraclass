package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author G. Neuteboom
 * Controller for the HTML page which gives the content of one Cohort.
 * Accessed by Teacher and Student.
 */

@Controller
public class CohortViewController {

    @GetMapping("/cohortView")
    public String showCohortView(Model model) {
        model.addAttribute("pageTitle", "Cohort View");
        model.addAttribute("cohortName", Cohort.getName())
        return "cohortView";
    }
}
