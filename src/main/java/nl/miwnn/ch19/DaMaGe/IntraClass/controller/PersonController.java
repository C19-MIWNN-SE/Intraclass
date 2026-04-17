package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author My Linh Lu
 * Manage elements for person page
 * @author Danylo Dudar
 * Manages person changes
 */
@Controller
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping( "/overview")
    public String personOverview(Model model) {

        List<Person> people;

        people = personRepository.findAll();

        model.addAttribute("pageTitle", "Person Overview");
        model.addAttribute("people", people);
        return "personOverview";
    }

    @GetMapping("/add/{type}")
    public String showAddForm(@PathVariable String type, Model model) {
        if ("student".equalsIgnoreCase(type)) {
            model.addAttribute("person", new Student());
        } else if ("teacher".equalsIgnoreCase(type)) {
            model.addAttribute("person", new Teacher());
        }
        return "person-add-edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("person", person);
        return "person-add-edit";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute Person person) {
//        if (person.getId() != null) {
//            Person existing = personRepository.findById(person.getId()).orElseThrow();
//            //Danylo was working here on Friday on solving some issues(WIP)
//            existing.setFirstName(person.getFirstName());
//            existing.setAffix(person.getAffix());
//            existing.setLastName(person.getLastName());
//            existing.setDateOfBirth(person.getDateOfBirth());
//            existing.setEmail(person.getEmail());
//
//            if (existing instanceof Student student && person instanceof Student pStudent) {
//                student.setBio(pStudent.getBio());
//                student.setEmployer(pStudent.getEmployer());
//                student.setCity(pStudent.getCity());
//            } else if (existing instanceof Teacher teacher && person instanceof Teacher pTeacher) {
//                teacher.setSpecialty(pTeacher.getSpecialty());
//            }
//
//            personRepository.save(existing);
//        } else {
//            personRepository.save(person);
//        }
        personRepository.save(person);
        return "redirect:/person/overview";
    }
}
