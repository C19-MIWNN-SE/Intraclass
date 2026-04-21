package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;


import nl.miwnn.ch19.DaMaGe.IntraClass.dto.PersonDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import org.hibernate.annotations.Subselect;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@Component
public abstract class PersonMapper {

    protected void personFields(PersonDTO dto,
                                PasswordEncoder encoder,
                                Person person) {

        person.setUsername(dto.getUsername());
        person.setPassword(encoder.encode(dto.getPassword()));
        person.setFirstName(dto.getFirstName());
        person.setAffix(dto.getAffix());
        person.setLastName(dto.getLastName());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setImage(dto.getImage());
        person.setEmail(dto.getEmail());
        person.setRole(dto.getRole());
    }
}