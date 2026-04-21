package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.StudentMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author My Linh Lu
 * Manage elements for students page
 */
@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentMapper studentMapper;
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;


    public StudentController(StudentMapper studentMapper,
                             PersonRepository personRepository,
                             ImageRepository imageRepository,
                             PasswordEncoder passwordEncoder) {
        this.studentMapper = studentMapper;
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        StudentDTO dto = new StudentDTO();

        return "student-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute StudentDTO dto,
                       @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType(imageFile.getContentType());
            imageRepository.save(image);
            dto.setImage(image);
        }
        dto.setRole("STUDENT");
        Student student = studentMapper.toStudent(dto, passwordEncoder);
        personRepository.save(student);

        return "redirect:/students";
    }
}
