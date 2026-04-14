package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Danylo Dudar
 *Extends Person as a Student(trust me)
 * Check validation folder for more info
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

    @ManyToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Cohort> cohort = new ArrayList<>();
}
