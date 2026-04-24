package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.StudentMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author My Linh Lu
 * Manage business logic for Student
 */
@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;

    public StudentService(StudentMapper studentMapper,
                          PersonRepository personRepository,
                          ImageRepository imageRepository,
                          PasswordEncoder passwordEncoder,
                          StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
    }

    public void saveStudent(StudentDTO dto, MultipartFile imageFile) throws IOException {

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
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }
}
