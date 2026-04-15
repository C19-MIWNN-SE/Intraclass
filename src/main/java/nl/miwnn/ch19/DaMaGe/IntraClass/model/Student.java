package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Danylo Dudar
 *
 */
@Entity
public class Student extends Person {
    @NotBlank(message = "Bio can't be blank")
    private String bio;

    private String employer;

    @NotBlank(message = "City can't be blank")
    private String city;

    @ManyToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Cohort> cohort = new ArrayList<>();

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

    public List<Cohort> getCohort() {
        return cohort;
    }

    public void setCohort(List<Cohort> cohort) {
        this.cohort = cohort;
    }
}
