package nl.miwnn.ch19.DaMaGe.IntraClass.service;


import nl.miwnn.ch19.DaMaGe.IntraClass.dto.CohortDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.CohortMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@ExtendWith(MockitoExtension.class)
class CohortServiceTest {

    @Mock
    private CohortRepository cohortRepository;

    @Mock
    private CohortMapper cohortMapper;

    @InjectMocks
    private CohortService cohortService;

    private Cohort cohort;
    private CohortDTO cohortDTO;

    @BeforeEach
    void setUp() {
        cohort = new Cohort();
        cohortDTO = new CohortDTO();
    }

    @Test
    @DisplayName("getStudents should filter and return only students from cohort")
    void getStudentsShouldFilterAndReturnOnlyStudents() {
        // Arrange
        Long id = 1L;
        Student student = new Student();
        Teacher teacher = new Teacher();
        cohort.setParticipants(new ArrayList<>(List.of(student, teacher)));

        when(cohortRepository.findById(id)).thenReturn(Optional.of(cohort));

        // Act
        List<Student> result = cohortService.getStudents(id);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(student));
    }

    @Test
    @DisplayName("addPersonToCohort should add person and save cohort")
    void addPersonToCohortShouldAddAndSave() {
        // Arrange
        Long id = 1L;
        Person person = new Student();
        cohort.setParticipants(new ArrayList<>());

        when(cohortRepository.findById(id)).thenReturn(Optional.of(cohort));

        // Act
        cohortService.addPersonToCohort(id, person);

        // Assert
        assertTrue(cohort.getParticipants().contains(person));
        verify(cohortRepository).save(cohort);
    }

    @Test
    @DisplayName("saveCohort should map DTO and save")
    void saveCohortShouldMapAndSave() {
        // Arrange
        when(cohortMapper.toCohort(cohortDTO)).thenReturn(cohort);

        // Act
        cohortService.saveCohort(cohortDTO);

        // Assert
        verify(cohortMapper).toCohort(cohortDTO);
        verify(cohortRepository).save(cohort);
    }

    @Test
    @DisplayName("getCohort returns existing cohort")
    void getCohortReturnsExistingCohort() {
        // Arrange
        Long id = 1L;
        when(cohortRepository.findById(id)).thenReturn(Optional.of(cohort));

        // Act
        Cohort result = cohortService.getCohort(id);

        // Assert
        assertEquals(cohort, result);
    }

    @Test
    @DisplayName("getCohort throws exception when not found")
    void getCohortThrowsExceptionWhenNotFound() {
        // Arrange
        Long id = 1L;
        when(cohortRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> cohortService.getCohort(id));
    }
}