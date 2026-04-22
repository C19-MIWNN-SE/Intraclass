package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import jakarta.servlet.http.HttpServletRequest;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.PersonDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.PersonMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author My Linh Lu
 * Manage elements for person page
 * @author Danylo Dudar
 * Controls people
 * Master of puppets, I'm pulling your strings
 * Twisting your mind and smashing your dreams
 * Blinded by me, you can't see a thing
 * Just call my name 'cause I'll hear you scream
 * Master, master
 * Obey your master
 */
@Controller
@RequestMapping("")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping({"student/overview", "teacher/overview"})
    public String personOverview(Model model, HttpServletRequest request) {

        List<Person> people;

        people = personRepository.findAll();

        model.addAttribute("pageTitle", "Person Overview");
        model.addAttribute("people", people);
        //model.addAttribute("newPerson", new PersonDTO());
//Funky code:
        if (request.getRequestURI().contains("/student/")) {
            return "student-overview";
        } else{
            return "teacher-overview";
        }
    }
}
//COMPLETELY USELESS CODE. THEREFORE, I PROCLAIM IT REBUNDANT AND FORSAKEN
//    @GetMapping("/add/{type}")
//    public String showAddForm(@PathVariable String type, Model model) {
//        if ("Student".equalsIgnoreCase(type)) {
//            model.addAttribute("person", new Student());
//            model.addAttribute("pageTitle", "Add new student");
//        } else if ("Teacher".equalsIgnoreCase(type)) {
//            model.addAttribute("person", new Teacher());
//            model.addAttribute("pageTitle", "Add new teacher");
//        }
//        return "person-add-edit";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Person person = personRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
//        model.addAttribute("person", person);
//        return "person-add-edit";
//    }
//}
