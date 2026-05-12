package nl.miwnn.ch19.DaMaGe.IntraClass.service;

import jakarta.transaction.Transactional;
import nl.miwnn.ch19.DaMaGe.IntraClass.dto.CohortDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.mapper.CohortMapper;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.CohortRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author My Linh Lu
 * Manage business logic for cohorts
 */
@Service
@Transactional
public class CohortService {
    private final CohortRepository cohortRepository;
    private final CohortMapper cohortMapper;
    private final PersonRepository personRepository;

    public CohortService(CohortRepository cohortRepository,
                         CohortMapper cohortMapper,
                         PersonRepository personRepository) {
        this.cohortRepository = cohortRepository;
        this.cohortMapper = cohortMapper;
        this.personRepository = personRepository;
    }

    public List<Student> getStudents(Long cohortId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        return cohort.getParticipants().stream()
                .filter(participant -> participant instanceof Student)
                .map(participant -> (Student) participant)
                .toList();
    }

    public List<Teacher> getTeachers(Long cohortId) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        return cohort.getParticipants().stream()
                .filter(participant -> participant instanceof Teacher)
                .map(participant -> (Teacher) participant)
                .toList();
    }

    public void addPersonToCohort(Long cohortId, Person person) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();

        cohort.getParticipants().add(person);
        cohortRepository.save(cohort);
    }
    public void removePersonFromCohort(Long cohortId, Person person) {
        Cohort cohort = cohortRepository.findById(cohortId)
                .orElseThrow();


        cohort.getParticipants().remove(person);
        cohortRepository.save(cohort);
    }

    public Cohort getCohort(Long id) {
        return cohortRepository.findById(id)
                .orElseThrow();
    }

    public void saveCohort(CohortDTO dto) {
        Cohort cohort = cohortMapper.toCohort(dto);

        List<Person> participants = new ArrayList<>();

        if (dto.getStudentIds() != null) {
            participants.addAll(
                    personRepository.findAllById(dto.getStudentIds())
            );
        }

        if (dto.getTeacherIds() != null) {
            participants.addAll(
                    personRepository.findAllById(dto.getTeacherIds())
            );
        }

        cohort.setParticipants(participants);

        cohortRepository.save(cohort);
    }

    public List<Cohort> getCohortsForPerson(Person person) {
        return cohortRepository.findByParticipantsContaining(person);
    }
}
