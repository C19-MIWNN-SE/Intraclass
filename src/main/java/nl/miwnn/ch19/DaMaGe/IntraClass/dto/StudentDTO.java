package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author My Linh Lu
 */
public class StudentDTO extends PersonDTO {
    @NotBlank(message = "Bio can't be blank")
    private String bio;
    private String employer;

    @NotBlank(message = "City can't be blank")
    private String city;

    public StudentDTO() {}

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }
}
