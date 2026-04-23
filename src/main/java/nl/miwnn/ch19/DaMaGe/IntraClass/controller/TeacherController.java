package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.TeacherMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.TeacherRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author My Linh Lu
 * Manage elements for teachers page
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherMapper teacherMapper;
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherMapper teacherMapper,
                             PersonRepository personRepository,
                             ImageRepository imageRepository,
                             PasswordEncoder passwordEncoder, TeacherRepository teacherRepository) {
        this.teacherMapper = teacherMapper;
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        TeacherDTO dto = new TeacherDTO();

        model.addAttribute("teacher", dto);
        return "teacher-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute TeacherDTO dto,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType(imageFile.getContentType());
            imageRepository.save(image);
            dto.setImage(image);
        }

        dto.setRole("TEACHER");
        Teacher teacher = teacherMapper.toTeacher(dto, passwordEncoder);
        redirectAttributes.addFlashAttribute("successMessage", "Change to teachers saved.");
        personRepository.save(teacher);

        return "redirect:/teacher/overview";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@ModelAttribute TeacherDTO dto,
                               @PathVariable Long id,
                               Model model) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));

        model.addAttribute("teacher", teacher);
        return "teacher-form";
    }
}
