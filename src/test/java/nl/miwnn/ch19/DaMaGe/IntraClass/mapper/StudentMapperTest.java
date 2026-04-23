package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author My Linh Lu
 */
class StudentMapperTest {

    private final StudentMapper studentMapper = new StudentMapper();
    PersonMapperTest personMapperTest = new PersonMapperTest();
    StudentDTO studentDTO = new StudentDTO();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        // Person fields
        personMapperTest.fillPersonFields(studentDTO);

        // Student fields
        studentDTO.setBio("This is a test bio");
        studentDTO.setEmployer("Test Employer");
        studentDTO.setCity("Groningen");
    }


    @Test
    @DisplayName("toStudent sets correct Bio, Employer and City")
    void toStudentSetsCorrectBioEmployerAndCity() {
        // Arrange

        // Act
        Student result = studentMapper.toStudent(studentDTO,passwordEncoder);

        // Assert
        assertAll("Student Mapping",
                () -> assertEquals("This is a test bio", result.getBio()),
                () -> assertEquals("Test Employer", result.getEmployer()),
                () -> assertEquals("Groningen",result.getCity()));
    }

    @Test
    @DisplayName("plainpassword is stored hashed and stored password matches hashed password")
    void plainpasswordIsStoredHashedAndStoredPasswordMatchesHashedPassword() {
        // Act
        Student result = studentMapper.toStudent(studentDTO,passwordEncoder);

        // Assert
        assertNotEquals("TestPassword",result.getPassword());
        assertTrue(passwordEncoder.matches("TestPassword",result.getPassword()));
    }

    @Test
    @DisplayName("personFields correctly maps shared attributes for Student")
    void personFieldsCorrectlyMapsSharedAttributesForStudent() {
        // Arrange

        // Act
        Student result = studentMapper.toStudent(studentDTO,passwordEncoder);

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