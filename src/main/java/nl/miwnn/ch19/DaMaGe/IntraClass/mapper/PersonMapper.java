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
public class PersonMapper {
    public Person toPerson(
            PersonDTO dto,
            PasswordEncoder passwordEncoder) {

        Person user = new Person();
        personFields(dto, passwordEncoder, user);
        return user;
    }

    protected static void personFields(PersonDTO dto, PasswordEncoder passwordEncoder, Person user) {
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setRole(dto.getRole());
        user.setImage(dto.getImage());
    }
}
