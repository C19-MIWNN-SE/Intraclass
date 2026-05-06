package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.TeacherMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.TeacherRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author My Linh Lu
 * Manage business logic for Teacher
 */
@Service
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherMapper teacherMapper,
                          PersonRepository personRepository,
                          ImageRepository imageRepository,
                          PasswordEncoder passwordEncoder,
                          TeacherRepository teacherRepository) {
        this.teacherMapper = teacherMapper;
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherRepository = teacherRepository;
    }

    public void saveTeacher(TeacherDTO dto,
                            MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType(imageFile.getContentType());
            imageRepository.save(image);
            dto.setImage(image);
        }

        dto.setRole("TEACHER");

        Teacher teacher = teacherMapper.toTeacher(dto, passwordEncoder);
        personRepository.save(teacher);
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }
}
