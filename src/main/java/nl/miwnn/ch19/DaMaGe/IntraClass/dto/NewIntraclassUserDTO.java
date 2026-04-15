package nl.miwnn.ch19.DaMaGe.IntraClass.dto;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

public class NewIntraclassUserDTO {
    private String username;
    private String plainPassword;
    private String role;

    public NewIntraclassUserDTO() {
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
}
