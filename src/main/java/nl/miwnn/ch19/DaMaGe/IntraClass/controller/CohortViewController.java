package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author G. Neuteboom
 * Controller for the HTML page which gives the content of ONE Cohort.
 * Accessed by Teacher and Student.
 */

// TODO eerst CohortOverview HTML maken, waar voor een cohort gekozen kan worden, zodat deze pagina geladen kan worden.

@Controller
public class CohortViewController {


    private static final Logger log =
            LoggerFactory.getLogger(CohortViewController.class);


    @GetMapping("/cohortView")
    public String showCohortView(Model model) {
        model.addAttribute("pageTitle", "Cohort View");
        return "cohortView";
    }

}
