package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.*;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

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
    private final PasswordEncoder passwordEncoder;

    public InitializeController(
            CohortRepository cohortRepository,
            PersonRepository personRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            PasswordEncoder passwordEncoder) {
        this.cohortRepository = cohortRepository;
        this.personRepository = personRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if (personRepository.count() == 0) { seedAll();}
    }

    private void seedAll() {
        seedCohort();
        seedPeople();
        seedManyToMany();
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

//    TODO dit moet nog eens worden aangepast (t/m regel 176):
    private int GetStart(int counter, int sublistsize){
        int retval = counter*sublistsize;
        return retval;
    }

    private int GetEnd(int counter, int listsize, int sublistsize){
        int retval = (counter +1)*sublistsize;
        if(retval > listsize){
            retval = listsize;
        }
        return retval;
    }

    private void seedManyToManyStudents(){
            List<Cohort> cohorts = cohortRepository.findAll();
            List<Student> students = studentRepository.findAll();

            int subListSize = (int) Math.ceil((double) students.size() / cohorts.size());
            int counter = 0;
                for(Cohort cohort : cohorts){

                    for(Student student : students.subList(GetStart(counter, subListSize),
                            GetEnd(counter, students.size(), subListSize))){
                        cohort.getStudent().add(student);
                    }
                    counter++;
                }



//
//            for (int i = 0; i < cohorts.size(); i++){
//                for (int j = 0; j < cohorts.size(); j++){
//                    Cohort cohort = cohorts.get(j);
//                    cohort.getStudent().add(students.get(i * j));
//                }
//            }
//            for (int i = 0; i < students.size(); i++) {
//                Cohort cohort = cohorts.get(i);
//                cohort.getStudent().add(students.get(i));
//
//                }


    }

    private void seedManyToManyTeachers (){
        List<Cohort> cohorts = cohortRepository.findAll();
        List<Teacher> teachers = teacherRepository.findAll();

        int subListSize = (int) Math.ceil((double) teachers.size() / cohorts.size());
        int counter = 0;
        for(Cohort cohort : cohorts){

            for(Teacher teacher : teachers.subList(GetStart(counter, subListSize),
                    GetEnd(counter, teachers.size(), subListSize))){
                cohort.getTeacher().add(teacher);
            }
            counter++;
        }
    }
}