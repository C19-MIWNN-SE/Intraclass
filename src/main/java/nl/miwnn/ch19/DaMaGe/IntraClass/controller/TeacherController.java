package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.TeacherService;
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

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
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

        teacherService.saveTeacher(dto,imageFile);
        redirectAttributes.addFlashAttribute("successMessage", "Change to teachers saved.");

        return "redirect:/teacher/overview";
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
