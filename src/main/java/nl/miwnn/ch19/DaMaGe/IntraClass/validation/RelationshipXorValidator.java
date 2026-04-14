package nl.miwnn.ch19.DaMaGe.IntraClass.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nl.miwnn.ch19.DaMaGe.IntraClass.model.Person;

/**
 * @author Danylo Dudar
 *Contains a function that performs XOR operation with persons student/teacher relationships
 *Generally mimics a XOR logic gate using conjuntion
 */

public class RelationshipXorValidator implements ConstraintValidator<ValidationXOR, Person> {
    @Override
    public boolean isValid(Person person, ConstraintValidatorContext context) {
        if (person == null) return true;

        boolean hasA = person.getStudent() != null;
        boolean hasB = person.getTeacher() != null;

        return hasA ^ hasB;
    }
}
