package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import jakarta.persistence.EntityNotFoundException;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.ImageRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author My Linh Lu
 * Manage business logic for a person
 *
 * Not all those who wander are lost.
 */
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;

    public PersonService(PersonRepository personRepository,
                         ImageRepository imageRepository) {
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));


        if (person.getImage() != null) {
            imageRepository.delete(person.getImage());
        }
        if (person instanceof Student student) {
            student.getCohort().forEach(cohort -> cohort.getStudent().remove(student));
            student.getCohort().clear();

        } else if (person instanceof Teacher teacher) {
            teacher.getCohort().forEach(cohort -> cohort.getTeacher().remove(teacher));
            teacher.getCohort().clear();
        }

        personRepository.delete(person);
    }

    public boolean personAllowedToDelete(Long id, String username) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            return false;
        }
        if (person.getUsername().equals(username)) {
            // Kamikaze prevention
            return false;
        }

        deletePerson(id);
        return true;
    }
}