package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Danylo Dudar
 *Extends Person as Teacher(in a specific way)
 * Check validation folder for more info
 */

@Entity
@Table
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank(message = "Teacher specialty cannot be blank")
    @Getter @Setter
    private String specialty;

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    @Getter @Setter
    private Person person;

    @ManyToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter @Setter
    private List<Cohort> cohort = new ArrayList<>();
}
