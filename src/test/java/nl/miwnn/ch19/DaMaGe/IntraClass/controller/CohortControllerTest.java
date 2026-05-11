package nl.miwnn.ch19.DaMaGe.IntraClass.controller;


import nl.miwnn.ch19.DaMaGe.IntraClass.dto.CohortDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.service.CohortService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@ExtendWith(MockitoExtension.class)
class CohortControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CohortRepository cohortRepository;

    @Mock
    private CohortService cohortService;

    @InjectMocks
    private CohortController cohortController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cohortController).build();
    }

    @Test
    @DisplayName("GET /cohort/overview - Success")
    void showCohortOverview_ShouldReturnOverviewView() throws Exception {
        when(cohortRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/cohort/overview"))
                .andExpect(status().isOk())
                .andExpect(view().name("cohortOverview"))
                .andExpect(model().attributeExists("cohorts"))
                .andExpect(model().attribute("pageTitle", "Cohort Overview"));
    }

    @Test
    @DisplayName("GET /cohort/view/{id} - Success")
    void showCohort_ShouldReturnViewWithData() throws Exception {
        Long id = 1L;
        Cohort cohort = new Cohort();
        cohort.setName("Java Class");

        when(cohortService.getCohort(id)).thenReturn(cohort);
        when(cohortService.getStudents(id)).thenReturn(List.of(new Student()));
        when(cohortService.getTeachers(id)).thenReturn(List.of(new Teacher()));

        mockMvc.perform(get("/cohort/view/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("cohortView"))
                .andExpect(model().attribute("cohort", cohort))
                .andExpect(model().attributeExists("students", "teachers"));
    }

    @Test
    @DisplayName("GET /cohort/view/{id} - Not Found Redirect")
    void showCohort_ShouldRedirectWhenNotFound() throws Exception {
        Long id = 1L;
        when(cohortService.getCohort(id)).thenReturn(null);

        mockMvc.perform(get("/cohort/view/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cohort/overview"));
    }

    @Test
    @DisplayName("POST /cohort/save - Success")
    void saveAddCohort_ShouldSaveAndReturnForm() throws Exception {
        mockMvc.perform(post("/cohort/save")
                        .flashAttr("cohortDTO", new CohortDTO()))
                .andExpect(status().isOk())
                .andExpect(view().name("cohort-form"));

        verify(cohortService).saveCohort(any(CohortDTO.class));
    }

    @Test
    @DisplayName("GET /cohort/edit/{id} - Success")
    void showEditForm_ShouldReturnFormWithCohort() throws Exception {
        Long id = 1L;
        Cohort cohort = new Cohort();
        when(cohortService.getCohort(id)).thenReturn(cohort);

        mockMvc.perform(get("/cohort/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("cohort-form"))
                .andExpect(model().attribute("cohort", cohort));
    }
}
