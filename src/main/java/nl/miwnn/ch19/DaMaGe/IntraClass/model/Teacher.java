package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Danylo Dudar
 *Extends Person as Teacher
 */

@Entity
@Table
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Teacher specialty cannot be blank")
    private String specialty;

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Person person;
}
