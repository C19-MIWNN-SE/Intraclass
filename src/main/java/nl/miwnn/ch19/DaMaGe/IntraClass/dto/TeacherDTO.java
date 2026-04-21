package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

/**
 * @author My Linh Lu
 */
public class TeacherDTO extends PersonDTO {
    private String specialty;

    public TeacherDTO() {}

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
