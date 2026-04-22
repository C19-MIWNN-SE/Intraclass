package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author My Linh Lu
 */
class TeacherMapperTest {

    private final TeacherMapper teacherMapper = new TeacherMapper();
    PersonMapperTest personMapperTest = new PersonMapperTest();
    TeacherDTO teacherDTO = new TeacherDTO();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        // Person fields
        personMapperTest.fillPersonFields(teacherDTO);

        // Teacher fields
        teacherDTO.setSpecialty("Test Specialty");
    }

    @Test
    @DisplayName("toTeacher sets correct Specialty")
    void toTeacherSetsCorrectSpecialty() {
        // Arrange

        // Act
        Teacher result = teacherMapper.toTeacher(teacherDTO, passwordEncoder);

        // Assert
        assertEquals("Test Specialty",result.getSpecialty());
    }

    @Test
    @DisplayName("plainpassword is stored hashed and stored password matches hashed password")
    void plainpasswordIsStoredHashedAndStoredPasswordMatchesHashedPassword() {
        // Act
        Teacher result = teacherMapper.toTeacher(teacherDTO,passwordEncoder);

        // Assert
        assertNotEquals("TestPassword",result.getPassword());
        assertTrue(passwordEncoder.matches("TestPassword",result.getPassword()));
    }

    @Test
    @DisplayName("personFields correctly maps shared attributes for Teacher")
    void personFieldsCorrectlyMapsSharedAttributesForTeacher() {
        // Arrange

        // Act
        Teacher result = teacherMapper.toTeacher(teacherDTO,passwordEncoder);

        // Assert
        assertAll("personFields",
                () -> assertEquals("Test Username", result.getUsername()),
                () -> assertEquals("Test FirstName", result.getFirstName()),
                () -> assertEquals("Test LastName", result.getLastName()),
                () -> assertEquals("Affix",result.getAffix()),
                () -> assertEquals(LocalDate.of(1900,1,1), result.getDateOfBirth()),
                () -> assertEquals("test@test.com", result.getEmail()),
                () -> assertEquals("STUDENT", result.getRole()));
    }
}