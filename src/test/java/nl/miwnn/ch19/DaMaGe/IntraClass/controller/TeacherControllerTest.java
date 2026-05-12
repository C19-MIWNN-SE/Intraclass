package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.TeacherService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * @author G. Neuteboom
 * Testing MVC for Teacher.
 */
@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    void editTeacher_shouldReturnEditTeacherForm() throws Exception {
        //Arrange
        Long id = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        when(teacherService.getTeacherById(id)).thenReturn(teacher);
        //Act & Assert 1
        mockMvc.perform(get("/student/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher-form"))
                .andExpect(model().attributeExists("teacher"));
        //Assert 2
        verify(teacherService).getTeacherById(id);

    }
}
