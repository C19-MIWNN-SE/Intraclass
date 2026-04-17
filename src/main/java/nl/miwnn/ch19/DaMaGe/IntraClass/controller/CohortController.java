package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author G. Neuteboom
 * For: cohortOverview.html (list of all cohorts) (access by teacher only),
 *      cohortView.html (list of persons in 1 cohort) (access by teacher and student),
 *
 */

@Controller
public class CohortController {

    private static final Logger log =
            LoggerFactory.getLogger(CohortController.class);

    private final List<Cohort> cohorts = new ArrayList<>();
    private final CohortRepository cohortRepository;
    public CohortController(CohortRepository cohortRepository) {
        this.cohortRepository = cohortRepository;
    }

    @GetMapping("/cohort/overview")
    public String showCohortOverview(Model model) {
        List<Cohort> cohorts = cohortRepository.findAll();
        log.debug("Cohort Overview called, {} Cohorts in database", cohorts.size());
        model.addAttribute("pageTitle", "Cohort Overview");
        model.addAttribute("cohorts", cohorts);
        return "cohortOverview";
    }

    @GetMapping("/cohort/{id}")
    public String showCohort(@PathVariable Long id, Model model) {
        log.debug("Retrieving details for cohort {}", id);
        Optional<Cohort> cohort = cohortRepository.findById(id);

        if (cohort.isEmpty()) {
            log.warn("Cohort not found {}", id);
            return "redirect:/cohort/overview";
        }

        model.addAttribute("cohort", cohort.get());
        return "cohortView";
    }

}
