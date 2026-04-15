package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.NewIntraclassUserDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.mapper.IntraclassUserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
@RequestMapping("/users")
public class IntraclassUserController {

    private final UserRepository userRepository;
    private final IntraclassUserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public IntraclassUserController(
            UserRepository userRepository,
            IntraclassUserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String showUserOverview(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("newUser", new NewIntraclassUserDTO());
        return "users/overview";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("newUser", new NewIntraclassUserDTO());
        return "users/add-user";
    }

    @PostMapping("/add") public
    String addUser(@ModelAttribute("newUser")
                   NewIntraclassUserDTO dto,
                   RedirectAttributes redirectAttributes) {
        userRepository.save(userMapper.toLibraryUser(dto, passwordEncoder));
        redirectAttributes.addFlashAttribute("successMessage",
                "User '" + dto.getUsername() + "'made.");
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes)
    {
        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "User removed.");
        return "redirect:/users";
    }
}
