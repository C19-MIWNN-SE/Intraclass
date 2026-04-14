package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Danylo Dudar
 *Extends Person as a Student
 */
@Entity
@Table
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bio can't be blank")
    private String bio;

    private String employer;

    @NotBlank(message = "City can't be blank")
    private String city;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Person person;
}
