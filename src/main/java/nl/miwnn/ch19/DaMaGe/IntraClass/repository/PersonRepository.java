package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Danylo Dudar
 *
 */

public interface PersonRepository extends JpaRepository<Person, Long> {

}
