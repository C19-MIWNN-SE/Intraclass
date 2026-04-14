package nl.miwnn.ch19.DaMaGe.IntraClass.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.IntraClassUser;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.PersonRepository;
import nl.miwnn.ch19.DaMaGe.IntraClass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
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

    private final PersonRepository personRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public InitializeController(
            PersonRepository personRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if(personRepository.count() == 0) {
            seedPeople();
        }
        if (userRepository.count() == 0) {
            IntraClassUser admin = new IntraClassUser(
                    "admin",
                    passwordEncoder.encode("geheim123"),
                    "ADMIN"
            );
            userRepository.save(admin);
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

