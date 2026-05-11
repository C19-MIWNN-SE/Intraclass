package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danylo Dudar
 *Contains cohort information
 */

@Entity
@Table
public class Cohort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CsvDate("yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CsvDate("yyyy-MM-dd")
    private LocalDate endDate;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany
    @JoinTable(
            name = "cohort_person",
            joinColumns = @JoinColumn(name = "cohort_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> participant = new ArrayList<>();

    public Cohort () {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Person> getParticipant() {
        return participant;
    }
    public void setParticipant(List<Person> participants) {
        this.participant = participants;
    }
}
