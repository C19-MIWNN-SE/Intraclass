package nl.miwnn.ch19.DaMaGe.IntraClass.service.mapper;


import nl.miwnn.ch19.DaMaGe.IntraClass.dto.NewIntraclassUserDTO;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.IntraClassUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@Component
public class IntraclassUserMapper {
    public IntraClassUser toIntraClassUser(
            NewIntraclassUserDTO dto,
            PasswordEncoder passwordEncoder) {

        IntraClassUser user = new IntraClassUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPlainPassword()));
        user.setRole(dto.getRole());
        user.setImage(dto.getImage());
        return user;
    }
}
