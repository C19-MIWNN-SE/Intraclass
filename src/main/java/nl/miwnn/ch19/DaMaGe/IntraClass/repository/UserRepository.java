package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.IntraClassUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author My Linh Lu
 */
public interface UserRepository extends JpaRepository<IntraClassUser, Long> {
    Optional<IntraClassUser> findByUsername(String username);
}