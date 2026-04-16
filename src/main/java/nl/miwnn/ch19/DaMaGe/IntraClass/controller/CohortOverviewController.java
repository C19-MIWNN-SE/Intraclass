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
 * Controller for the HTML page which gives a list of ALL Cohorts.
 * Accessed by Teacher only.
 */

@Controller
public class CohortOverviewController {

    private static final Logger log =
            LoggerFactory.getLogger(CohortOverviewController.class);

    private final List<Cohort> cohorts = new ArrayList<>();

    @GetMapping("/cohortOverview")
    public String showCohortOverview(Model model) {
        log.debug("Cohort Overview called, {} Cohorts in database", cohorts.size());
        model.addAttribute("pageTitle", "Cohort Overview");
        model.addAttribute("cohortName", cohorts);
        return "cohortView";
    }
}
