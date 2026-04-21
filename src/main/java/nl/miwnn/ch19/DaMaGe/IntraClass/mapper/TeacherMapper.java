package nl.miwnn.ch19.DaMaGe.IntraClass.mapper;

import nl.miwnn.ch19.DaMaGe.IntraClass.dto.StudentDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.TeacherDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author My Linh Lu
 */
@Component
public class TeacherMapper extends PersonMapper {
    public Teacher toTeacher(
            TeacherDTO dto,
            PasswordEncoder passwordEncoder) {

        Teacher teacher = new Teacher();
        personFields(dto, passwordEncoder, teacher);
        teacher.setSpecialty(dto.getSpeciality());

        return teacher;
    }
}
