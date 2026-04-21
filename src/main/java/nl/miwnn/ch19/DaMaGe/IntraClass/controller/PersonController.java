package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.PersonDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.PersonMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public PersonController(PersonRepository personRepository,
                            PersonMapper personMapper,
                            PasswordEncoder passwordEncoder,
                            ImageRepository imageRepository) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @GetMapping( "/overview")
    public String personOverview(Model model) {

        List<Person> people;

        people = personRepository.findAll();

        model.addAttribute("pageTitle", "Person Overview");
        model.addAttribute("people", people);
        model.addAttribute("newPerson", new PersonDTO());
        return "personOverview";
    }

    @GetMapping("/add/{type}")
    public String showAddForm(@PathVariable String type, Model model) {
        if ("Student".equalsIgnoreCase(type)) {
            model.addAttribute("person", new Student());
            model.addAttribute("pageTitle", "Add new student");
        } else if ("Teacher".equalsIgnoreCase(type)) {
            model.addAttribute("person", new Teacher());
            model.addAttribute("pageTitle", "Add new teacher");
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
    public String savePerson(@ModelAttribute ("newPerson")
                             PersonDTO dto,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("imageFile")MultipartFile imageFile) throws IOException {

        if(!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType((imageFile.getContentType()));
            imageRepository.save(image);
            dto.setImage(image);
        }

        personRepository.save(personMapper.toPerson(dto, passwordEncoder));
        redirectAttributes.addFlashAttribute("successMessage",
                "User '" + dto.getUsername() + "'created.");
        return "redirect:/person/overview";
    }
}
