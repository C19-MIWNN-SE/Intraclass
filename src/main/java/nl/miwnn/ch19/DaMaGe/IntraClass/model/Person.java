package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @author My Linh Lu
 * Contains information about a person who is a participant of the school
 */
@Entity
@Table
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name can't be blank")
    private String firstName;

    private String affix;

    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @NotNull(message = "Date of birth can't be blank")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private Student student;

    @OneToOne(fetch = FetchType.EAGER)
    private Teacher teacher;

}
