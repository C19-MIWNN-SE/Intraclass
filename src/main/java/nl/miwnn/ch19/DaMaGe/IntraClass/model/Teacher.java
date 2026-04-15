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
    private Long id;

    @NotBlank(message = "Teacher specialty cannot be blank")
    private String specialty;

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Person person;

    @ManyToMany(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Cohort> cohort = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Cohort> getCohort() {
        return cohort;
    }

    public void setCohort(List<Cohort> cohort) {
        this.cohort = cohort;
    }
}
