package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Danylo Dudar
 *
 */

@Controller
public class InitializeController {

    private final CohortRepository cohortRepository;

    private final PersonRepository personRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public InitializeController(
            CohortRepository cohortRepository,
            PersonRepository personRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.cohortRepository = cohortRepository;
        this.personRepository = personRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if (personRepository.count() == 0) { seedAll();}
        if (userRepository.count() == 0) {
            IntraClassUser admin = new IntraClassUser(
                    "admin",
                    passwordEncoder.encode("geheim123"),
                    "ADMIN"
            );
            userRepository.save(admin);
        }
    }

    private void seedAll() {
        seedCohort();
        seedPeople();
    }

    private void seedPeople() {
        seedTable(
                "student.csv",
                Student.class,
                studentRepository
        );
        seedTable(
                "teacher.csv",
                Teacher.class,
                teacherRepository
        );
    }

    private void seedCohort() {
        seedTable(
                "cohort.csv",
                Cohort.class,
                cohortRepository
        );
    }

    //DRY function for data entry. Ask Danylo if it needs to be modified
    private <T> void seedTable(String tableName,
                               Class<T> className,
                               JpaRepository<T, ?> repository) {
        try {
            ClassPathResource resource =
                    new ClassPathResource(tableName);
            Reader reader = new InputStreamReader(resource.getInputStream());
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withType(className)
                    .withIgnoreLeadingWhiteSpace(true).build();
            repository.saveAll(csvToBean.parse());
        } catch (IOException e) {
            throw new RuntimeException("Can not read "+ tableName, e);
        }
    }
}