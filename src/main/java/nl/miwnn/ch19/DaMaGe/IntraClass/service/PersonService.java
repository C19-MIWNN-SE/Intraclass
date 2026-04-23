package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author My Linh Lu
 * Manage business logic for a person
 *
 * Not all those who wander are lost.
 */
@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Transactional
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

//        if (person.getImage() != null) {
//            imageRepository.delete(person.getImage());
//        }
        if (person instanceof Student student) {
            student.getCohort().forEach(cohort -> cohort.getStudent().remove(student));
            student.getCohort().clear();

        } else if (person instanceof Teacher teacher) {
            teacher.getCohort().forEach(cohort -> cohort.getTeacher().remove(teacher));
            teacher.getCohort().clear();
        }

        personRepository.delete(person);
    }
}