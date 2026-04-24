package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import jakarta.validation.Valid;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.StudentMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.StudentRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.PersonService;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author My Linh Lu
 * Manage elements for students page
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/add")
    public String showForm(@ModelAttribute StudentDTO dto,
                           Model model) {
        model.addAttribute("student", new StudentDTO());
        dto.setRole("STUDENT");

        return "student-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute StudentDTO dto,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        studentService.saveStudent(dto, imageFile);
        redirectAttributes.addFlashAttribute("successMessage", "Change to students saved.");

        return "redirect:/student/overview";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@ModelAttribute StudentDTO dto,
                               @PathVariable Long id,
                               Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "student-form";
    }
}
