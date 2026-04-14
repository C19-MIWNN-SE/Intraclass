package nl.miwnn.ch19.DaMaGe.IntraClass.model;

import com.opencsv.bean.CsvDate;
import jakarta.persistence.*;

import java.time.LocalDate;

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

    private String description;

    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate startDate;

    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate endDate;


}
