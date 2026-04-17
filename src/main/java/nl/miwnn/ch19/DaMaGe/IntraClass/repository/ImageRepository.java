package nl.miwnn.ch19.DaMaGe.IntraClass.repository;

import nl.miwnn.ch19.DaMaGe.IntraClass.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author My Linh Lu
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
