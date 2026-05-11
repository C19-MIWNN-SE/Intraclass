package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.CohortDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.CohortService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

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

    private final CohortRepository cohortRepository;
    private final CohortService cohortService;

    public CohortController(CohortRepository cohortRepository,
                            CohortService cohortService) {
        this.cohortService = cohortService;
        this.cohortRepository = cohortRepository;
    }

    @GetMapping("/cohort/overview")
    public String showCohortOverview(@ModelAttribute CohortDTO dto, Model model) {
        List<Cohort> cohorts = cohortRepository.findAll();
        log.debug("Cohort Overview called, {} Cohorts in database", cohorts.size());
        model.addAttribute("pageTitle", "Cohort Overview");
        model.addAttribute("activePage", "cohorts");
        model.addAttribute("cohorts", cohorts);
        model.addAttribute("cohort", new CohortDTO());
        return "cohortOverview";
    }

    @GetMapping("/cohort/view/{id}")
    public String showCohort(@PathVariable Long id, Model model) {
        log.debug("Retrieving details for cohort {}", id);

        Cohort cohort = cohortService.getCohort(id);

        if (cohort == null) {
            log.warn("Cohort not found {}", id);
            return "redirect:/cohort/overview";
        }

        List<Student> students = cohortService.getStudents(id);
        List<Teacher> teachers = cohortService.getTeachers(id);

        model.addAttribute("pageTitle", "Cohort: " + cohort.getName());
        model.addAttribute("cohort", cohort);
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);

        return "cohortView";
    }

    @GetMapping("/cohort/add")
    public String showAddCohortForm(@ModelAttribute CohortDTO dto, Model model){
        log.debug("Form for new Cohort requested");
        model.addAttribute("cohort", new CohortDTO());
        return "redirect:/cohort/overview";
    }

    @PostMapping("/cohort/save")
    public String saveAddCohort(@ModelAttribute CohortDTO cohortDTO) {
        log.info("New Cohort added: {}", cohortDTO.getName());

        cohortService.saveCohort(cohortDTO);

        return "cohort-form";
    }

    @GetMapping("/cohort/edit/{id}")
    public String showEditForm(@ModelAttribute CohortDTO cohortDTO,
                               @PathVariable Long id,
                               Model model) {
        Cohort cohort = cohortService.getCohort(id);
        model.addAttribute("cohort", cohort);

        return "cohort-form";
    }

}
