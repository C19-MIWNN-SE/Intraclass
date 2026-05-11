package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.StudentMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author My Linh Lu
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach

    void setUp() {
        studentDTO = new StudentDTO();
        student = new Student();
    }

    @Test
    @DisplayName("saveStudent without profile image should set Student and save")
    void saveStudentWithoutProfileImageShouldSetStudentAndSave() throws IOException {
        // Arrange
        MockMultipartFile noImage = new MockMultipartFile("image", new byte[0]);

        // Act
        studentService.saveStudent(studentDTO, noImage);

        // Assert

    }
}