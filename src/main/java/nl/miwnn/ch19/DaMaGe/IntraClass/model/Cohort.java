package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @CsvDate(value = "yyyy-MM-dd")
    @Getter @Setter
    private LocalDate startDate;

    @CsvDate(value = "yyyy-MM-dd")
    @Getter @Setter
    private LocalDate endDate;

    @ManyToMany
    @Getter @Setter
    private List<Student>  student = new ArrayList<>();

    @ManyToMany
    @Getter @Setter
    private List<Teacher> teacher = new ArrayList<>();
}
