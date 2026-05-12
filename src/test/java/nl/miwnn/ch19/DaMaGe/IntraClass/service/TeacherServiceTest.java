package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.TeacherMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * @author G. Neuteboom
 * Test for TeacherService.
 */
@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private ImageRepository imageRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TeacherService teacherService;

    private TeacherDTO teacherDTO;

    private Teacher teacher;

    @BeforeEach
    void setUp(){
        teacherDTO = new TeacherDTO();
        teacher = new Teacher();
    }

    @Test
    void givenTeacherWithoutImage_whenSavingTeacher_thenTeacherSavesWithDefaultImage() throws IOException {
        //Arrange
        MockMultipartFile imageMissing = new MockMultipartFile("imageMissing", new byte[0]);
        when(teacherMapper.toTeacher(any(), eq(passwordEncoder))).thenReturn(teacher);
        //Act
        teacherService.saveTeacher(teacherDTO, imageMissing);
        //Assert
        assertEquals("TEACHER", teacherDTO.getRole());
        verify(imageRepository, never()).save(any());
        verify(personRepository).save(teacher);
    }

    @Test
    void givenSpecificStudentID_whenSearchingForStudent_thenCorrectStudentIsFound(){
        //Arrange
        Long id = 1L;
        Teacher teacher = new Teacher();
        when(teacherRepository.findById(id))
                .thenReturn(Optional.of(teacher));

        // Act
        Teacher specificTeacher = teacherService.getTeacherById(id);

        // Assert
        assertEquals(teacher,specificTeacher);
        verify(teacherRepository).findById(id);
    }
}
