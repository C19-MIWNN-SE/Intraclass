package nl.miwnn.ch19.DaMaGe.IntraClass.service;


import nl.miwnn.ch19.DaMaGe.IntraClass.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Danylo Dudar
 * <p>
 * Sunrise, Parabellum.
 */

@Service
public class IntraclassUserService implements UserDetailsService {
    private final UserRepository userRepository;

    public IntraclassUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
