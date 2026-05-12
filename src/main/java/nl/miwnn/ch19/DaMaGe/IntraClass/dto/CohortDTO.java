package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;

import java.time.LocalDate;
import java.util.List;

/**
 * @author G. Neuteboom
 * Receiving and sending Cohort data to and from the database.
 */
public class CohortDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Person> participant;
    private List<Long> studentIds;
    private List<Long> teacherIds;

    public CohortDTO() {

    }

    public CohortDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public void setParticipant(List<Person> participant) {
        this.participant = participant;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Long> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<Long> teacherIds) {
        this.teacherIds = teacherIds;
    }
}
