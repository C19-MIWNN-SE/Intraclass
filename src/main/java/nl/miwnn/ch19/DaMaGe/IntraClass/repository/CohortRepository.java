package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Danylo Dudar
 *
 */

public interface CohortRepository extends JpaRepository<Cohort, Long> {
    List<Cohort> findByParticipantsContaining(Person person);
}
