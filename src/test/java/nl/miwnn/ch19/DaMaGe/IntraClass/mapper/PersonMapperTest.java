package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.PersonDTO;

import java.time.LocalDate;

/**
 * @author My Linh Lu
 * !! BESCHRIJVING !!
 */
public class PersonMapperTest {

    public void fillPersonFields(PersonDTO dto) {
        dto.setId(1L);
        dto.setUsername("Test Username");
        dto.setPassword("TestPassword");
        dto.setFirstName("Test FirstName");
        dto.setLastName("Test LastName");
        dto.setAffix("Affix");
        dto.setDateOfBirth(LocalDate.of(1900,1,1));
        // TODO setImage testing?
        dto.setEmail("test@test.com");
        dto.setRole("STUDENT");
    }
}
