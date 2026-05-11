package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author My Linh Lu
 */
public class TeacherDTO extends PersonDTO {
    @NotBlank(message = "Teacher specialty cannot be blank")
    private String specialty;

    public TeacherDTO() {}

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
