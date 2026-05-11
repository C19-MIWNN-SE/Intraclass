package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import jakarta.validation.Valid;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/add")
    public String showForm(@ModelAttribute TeacherDTO dto,
                           Model model) {
        if (!model.containsAttribute("teacher")) {
            model.addAttribute("teacher", new TeacherDTO());
        }

        dto.setRole("TEACHER");

        return "teacher-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute TeacherDTO dto,
                       BindingResult bindingResult,
                       @RequestParam("imageFile") MultipartFile imageFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.teacher",
                    bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", "Error while saving. Please try again.");
            return "redirect:/login";
        }

        teacherService.saveTeacher(dto,imageFile);
        redirectAttributes.addFlashAttribute("successMessage", "Change to teachers saved.");

        return "redirect:/login";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@ModelAttribute TeacherDTO dto,
                               @PathVariable Long id,
                               Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);

        return "teacher-form";
    }
}
