package nl.miwnn.ch19.DaMaGe.IntraClass.controller;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author My Linh Lu
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    @DisplayName("showForm should return StudentForm")
    void showForm_shouldReturnStudentForm() throws Exception {
        mockMvc.perform(get("/student/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-form"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @DisplayName("save should redirect to student overview")
    void saveShouldRedirectToStudentOverview() throws Exception {
        MockMultipartFile imageFile =
                new MockMultipartFile(
                        "imageFile",
                        "mockImage".getBytes()
                );

        mockMvc.perform(multipart("/student/save")
                .file(imageFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student/overview"))
                .andExpect(flash().attributeExists("successMessage"));

        verify(studentService).saveStudent(any(StudentDTO.class), any(MultipartFile.class));
    }

    @Test
    @DisplayName("showEditForm should return StudentForm")
    void showEditFormShouldReturnStudentForm() throws Exception {
        // Arrange
        Long id = 1L;

        Student student = new Student();
        student.setId(id);
        when(studentService.getStudentById(id)).thenReturn(student);

        // Act + Assert
        mockMvc.perform(get("/student/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-form"))
                .andExpect(model().attributeExists("student"));

        verify(studentService).getStudentById(id);
    }
}