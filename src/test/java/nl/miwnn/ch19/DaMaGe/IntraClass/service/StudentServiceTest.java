package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.StudentMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
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

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author My Linh Lu
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private StudentRepository studentRepository;

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
        MockMultipartFile noImage = new MockMultipartFile("noImage", new byte[0]);
        when(studentMapper.toStudent(any(), eq(passwordEncoder))).thenReturn(student);

        // Act
        studentService.saveStudent(studentDTO, noImage);

        // Assert
        assertEquals("STUDENT", studentDTO.getRole());
        verify(imageRepository, never()).save(any());
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("saveStudent with profile image should set Student and save")
    void saveStudentWithProfileImageShouldSetStudentAndSave() throws IOException {
        // Arrange
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test-image-content".getBytes());
        when(studentMapper.toStudent(any(), eq(passwordEncoder))).thenReturn(student);

        // Act
        studentService.saveStudent(studentDTO, imageFile);

        // Assert
        assertEquals("STUDENT", studentDTO.getRole());
        verify(imageRepository).save(any(Image.class));
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("getStudentById returns saved student")
    void getStudentByIdReturnsSavedStudent() {
        // Arrange
        Long id = 1L;
        Student student = new Student();

        when(studentRepository.findById(id))
                .thenReturn(Optional.of(student));

        // Act
        Student studentWithId = studentService.getStudentById(id);

        // Assert
        assertEquals(student,studentWithId);
        verify(studentRepository).findById(id);
    }

    @Test
    @DisplayName("getStudentById throws error when id does not exist")
    void getStudentByIdThrowsErrorWhenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(studentRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {studentService.getStudentById(id);});

        // Assert
        assertEquals("Invalid student Id: " + id, exception.getMessage());
    }
}