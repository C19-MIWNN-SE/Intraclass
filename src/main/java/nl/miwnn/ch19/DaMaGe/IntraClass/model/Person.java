package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.miwnn.ch19.DaMaGe.IntraClass.validation.ValidationXOR;

import java.time.LocalDate;

/**
 * @author My Linh Lu
 * Contains information about a person who is a participant of the school
 */
@Entity
@Table
@ValidationXOR //This is a custom validator, not fully tested yet
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank(message = "First name can't be blank")
    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String affix;

    @NotBlank(message = "Last name can't be blank")
    @Getter @Setter
    private String lastName;

    @NotNull(message = "Date of birth can't be blank")
    @CsvDate(value = "yyyy-MM-dd")
    @Getter @Setter
    private LocalDate dateOfBirth;

    @Getter @Setter
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @Getter @Setter
    private Student student;

    @OneToOne(fetch = FetchType.EAGER)
    @Getter @Setter
    private Teacher teacher;
}
