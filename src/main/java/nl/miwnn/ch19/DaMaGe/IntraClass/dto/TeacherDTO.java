package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

/**
 * @author My Linh Lu
 */
public class TeacherDTO extends PersonDTO{
    private String speciality;

    public TeacherDTO() {}

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
