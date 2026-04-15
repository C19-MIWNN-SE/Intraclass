package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
