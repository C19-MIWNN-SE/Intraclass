package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import jakarta.transaction.Transactional;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Cohort;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Student;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Teacher;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author My Linh Lu
 * Manage business logic for cohorts
 */
@Service
@Transactional
public class CohortService {
    private final CohortRepository cohortRepository;

    public CohortService(CohortRepository cohortRepository) {
        this.cohortRepository = cohortRepository;
    }

    public List<Student> getStudents(Long cohortId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        return cohort.getParticipant().stream()
                .filter(participant -> participant instanceof Student)
                .map(participant -> (Student) participant)
                .toList();
    }

    public List<Teacher> getTeachers(Long cohortId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        return cohort.getParticipant().stream()
                .filter(participant -> participant instanceof Teacher)
                .map(participant -> (Teacher) participant)
                .toList();
    }

    public void addPersonToCohort(Long cohortId, Person person) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        cohort.getParticipant().add(person);
        cohortRepository.save(cohort);
    }

    public void removePersonFromCohort(Long cohortId, Person person) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        cohort.getParticipant().remove(person);
        cohortRepository.save(cohort);
    }

    public Cohort getCohort(Long id) {
        return cohortRepository.findById(id)
                .orElseThrow();
    }
}
