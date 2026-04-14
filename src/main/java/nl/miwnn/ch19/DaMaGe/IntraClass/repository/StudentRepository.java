package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Danylo Dudar
 *
 */


public interface StudentRepository extends JpaRepository<Student, Long> {
}
