package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    private String affix;

    @NotBlank(message = "City can't be blank")
    private String city;

//  TODO
//    @NotBlank(message = "A profile image is required")
//    private Image profilePicture;

    @NotBlank(message = "Bio can't be blank")
    private String bio;

//  TODO
//    @NotBlank(message = "Employer can't be blank")
//    private Employer employer;

    @NotBlank(message = "Date of birth can't be blank")
    private LocalDate dateOfBirth;
}
