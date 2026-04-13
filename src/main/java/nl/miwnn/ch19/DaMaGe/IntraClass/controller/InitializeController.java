package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
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

    private final PersonRepository personRepository;


    public InitializeController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if(personRepository.count() == 0) {
            seedPeople();
        }
    }

    private void seedPeople() {
        try {
            ClassPathResource resource =
                    new ClassPathResource("people.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());
            CsvToBean<Person> csvToBean = new CsvToBeanBuilder<Person>(reader)
                    .withType(Person.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            personRepository.saveAll(csvToBean.parse());
        } catch (IOException e) {
            throw new RuntimeException("Kon people.csv niet inlezen", e);
        }
    }
}

