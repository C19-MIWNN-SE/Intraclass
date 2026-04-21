package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author My Linh Lu
 * Danylo wants jokes but I'm boring
 */
@Component
public class StudentMapper extends PersonMapper {
    public Student toStudent(
            StudentDTO dto, PasswordEncoder passwordEncoder) {

        Student student = new Student();
        personFields(dto, passwordEncoder, student);
        student.setBio(dto.getBio());
        student.setEmployer(dto.getEmployer());
        student.setCity(dto.getCity());

        return student;
    }
}
