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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.*;

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

    public InitializeController(
            CohortRepository cohortRepository,
            PersonRepository personRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository) {
        this.cohortRepository = cohortRepository;
        this.personRepository = personRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }


    @Transactional
    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if (personRepository.count() == 0) { seedAll();}
    }

    private void seedAll() {
        seedCohort();
        seedPeople();
        seedImagesFromCsv();
        seedRelationships();
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

    private void seedRelationships() {

        List<Cohort> cohorts = cohortRepository.findAll();

        List<Student> students = studentRepository.findAll();
        List<Teacher> teachers = teacherRepository.findAll();

        if (cohorts.isEmpty() || students.isEmpty() || teachers.isEmpty()) {
            throw new IllegalStateException("Missing seed data for cohort relationships");
        }

        Random random = new Random();

        Map<Long, List<Cohort>> studentAssignments = new HashMap<>();

        for (Student student : students) {
            studentAssignments.put(student.getId(), new ArrayList<>());
        }

        for (Cohort cohort : cohorts) {

            Collections.shuffle(teachers);

            int teacherCount = Math.min(teachers.size(), random.nextInt(3) + 1);

            for (int i = 0; i < teacherCount; i++) {
                cohort.getParticipants().add(teachers.get(i));
            }

            Collections.shuffle(students);

            int studentCount = random.nextInt(16) + 10;

            int added = 0;

            for (Student student : students) {

                if (added >= studentCount) {
                    break;
                }

                List<Cohort> existing = studentAssignments.get(student.getId());

                boolean overlaps = existing.stream().anyMatch(c ->
                        datesOverlap(
                                cohort.getStartDate(),
                                cohort.getEndDate(),
                                c.getStartDate(),
                                c.getEndDate()
                        )
                );

                if (!overlaps) {
                    cohort.getParticipants().add(student);
                    existing.add(cohort);
                    added++;
                }
            }
        }

        cohortRepository.saveAll(cohorts);
    }

    private boolean datesOverlap(
            LocalDate start1,
            LocalDate end1,
            LocalDate start2,
            LocalDate end2
    ) {
        if (start1 == null || start2 == null) {
            return false;
        }

        if (end1 == null) {
            end1 = LocalDate.MAX;
        }

        if (end2 == null) {
            end2 = LocalDate.MAX;
        }

        return !(end1.isBefore(start2) || end2.isBefore(start1));
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

    private Image loadImage(String imageUrl) throws IOException {
        String filename = "static/img/" + imageUrl;
        ClassPathResource resource = new ClassPathResource(filename);

        //triple embedded ternary operator. Sorry for adding this - Danylo
        String contentType = imageUrl.toLowerCase().endsWith(".png") ? "image/png" :
                        imageUrl.toLowerCase().endsWith(".gif") ? "image/gif" :
                        imageUrl.toLowerCase().endsWith(".jpg")? "image/jpg" : "image/jpeg";

        Image image = new Image();
        image.setData(resource.getInputStream().readAllBytes());
        image.setContentType(contentType);
        return image;
    }

    public void seedImagesFromCsv() {
        String csvFile = "image-mapping.csv";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(csvFile).getInputStream()))) {

            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 2) continue;

                Long id = Long.parseLong(data[0].trim());
                String fileName = data[1].trim();

                personRepository.findById(id).ifPresent(person -> {
                    assignImageToPerson(person, fileName);
                    personRepository.save(person);
                });
            }
        } catch (IOException e) {
            System.err.println("Could not load image mapping CSV: " + e.getMessage());
        }
    }

    //I am ungodly frustrated while writing this - Danylo
    private void assignImageToPerson(Person person, String imagePath) {
        try {
            Image image = loadImage(imagePath);
            ((Person) person).setImage(image);//for future cleanup, Person cast is not rebundant
        } catch (IOException e) {
            System.err.println("Skipping image: " + imagePath + " (File not found)");
        }
    }
}