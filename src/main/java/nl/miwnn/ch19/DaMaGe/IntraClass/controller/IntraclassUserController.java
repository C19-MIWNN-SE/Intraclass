package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.PersonDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.PersonMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author Danylo Dudar
 * Controlls users
 * Master of puppets, I'm pulling your strings
 * Twisting your mind and smashing your dreams
 * Blinded by me, you can't see a thing
 * Just call my name 'cause I'll hear you scream
 * Master, master
 * Obey your master
 */
//TODO user is being merged with person, things will change here. It will get ugly
@Controller
@RequestMapping("/user")
public class IntraclassUserController {

    private final PersonRepository personRepository;
    private final PersonMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public IntraclassUserController(
            PersonRepository personRepository,
            PersonMapper personMapper,
            PasswordEncoder passwordEncoder,
            ImageRepository imageRepository) {
        this.personRepository = personRepository;
        this.userMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @GetMapping({"/", "/all"})
    public String showUserOverview(Model model) {
        model.addAttribute("users", personRepository.findAll());
        model.addAttribute("newUser", new PersonDTO());
        return "user-overview";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("newUser", new PersonDTO());
        return "user-add";
    }

    @PostMapping("/save") public
    String addUser(@ModelAttribute("newUser")
                   PersonDTO dto,
                   RedirectAttributes redirectAttributes,
                   @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if(!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType((imageFile.getContentType()));
            imageRepository.save(image);
            dto.setImage(image);
        }

        personRepository.save(userMapper.toPerson(dto, passwordEncoder));
        redirectAttributes.addFlashAttribute("successMessage",
                "User '" + dto.getUsername() + "'created.");
        return "redirect:/user/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes)
    {
        personRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted.");
        return "redirect:/user/all";
    }
}
