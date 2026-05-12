package nl.miwnn.ch19.DaMaGe.IntraClass.controller;


import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.CohortService;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@Controller
@RequestMapping("")
public class CohortAddRemoveController {
    private final CohortRepository cohortRepository;
    private final PersonRepository personRepository;

    public CohortAddRemoveController(CohortRepository cohortRepository, PersonRepository personRepository) {
        this.cohortRepository = cohortRepository;
        this.personRepository = personRepository;
    }

    @PostMapping("/cohort/view/{cohortId}/addToCohort/{personId}")
    public String addPersonToCohort(@PathVariable Long cohortId, @PathVariable Long personId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cohort ID: " + cohortId));
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person ID: " + personId));
        cohort.getParticipants().add(person);
        person.getCohort().add(cohort);
        cohortRepository.save(cohort);

        return "redirect:/cohort/view/" + cohortId;
    }
}
