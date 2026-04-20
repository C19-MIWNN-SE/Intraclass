package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

public class PersonDTO {
    private String username;
    private String plainPassword;
    private String role;
    private Image image;

    public PersonDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
