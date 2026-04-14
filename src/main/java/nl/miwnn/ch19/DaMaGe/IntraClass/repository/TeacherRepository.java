package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Danylo Dudar
 *
 */

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
