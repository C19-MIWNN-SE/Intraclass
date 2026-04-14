package nl.miwnn.ch19.DaMaGe.IntraClass.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Danylo Dudar
 *Implements a custom constraint for Person class that makes sure that person is EITHER
 * a teacher OR a student using logical XOR validation
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RelationshipXorValidator.class)
@Documented
public @interface ValidationXOR {
    String message() default "Must have exactly one relationship (not both or neither)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


