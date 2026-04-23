package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import jakarta.servlet.http.HttpServletRequest;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final PersonService personService;

    public PersonController(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    @GetMapping({"student/overview", "teacher/overview"})
    public String personOverview(Model model, HttpServletRequest request) {

        List<Person> people;

        people = personRepository.findAll();

        model.addAttribute("pageTitle", "Person Overview");
        model.addAttribute("people", people);
        //model.addAttribute("newPerson", new PersonDTO());
        //I love ternary operator -(c)Danylo
        return request.getRequestURI().contains("/student/") ?
                "student-overview" :
                "teacher-overview";
    }

    @GetMapping({"student/delete/{id}", "teacher/delete/{id}"})
    public String remove(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Person personToDelete = personRepository.findById(id).orElse(null);

        if (personToDelete != null && personToDelete.getUsername().equals(currentUsername)) {
            // Kamikaze prevention
            redirectAttributes.addFlashAttribute("errorMessage", "You cannot delete yourself.");
        } else {
            personService.deletePerson(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted.");
        }

        return request.getRequestURI().contains("/student/") ?
                "redirect:/student/overview" :
                "redirect:/teacher/overview";
    }
}