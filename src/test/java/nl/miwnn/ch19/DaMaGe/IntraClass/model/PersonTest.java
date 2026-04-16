package nl.miwnn.ch19.DaMaGe.IntraClass.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Danylo Dudar
 * tests Person object
 * Sunrise, Parabellum.
 */

public class PersonTest {
    @Test
    @DisplayName("getFullName should combine first and last names with affix")
    void getFullNameShouldCombineNames() {
        Person person = new Person();
        person.setFullName("FirstName","affix","LastName");

        assertEquals("FirstName affix LastName", person.getFullName());
    }

    @Test
    @DisplayName("getFullName should combine first and last names without affix")
    void getFullNameShouldCombineNamesWithoutAffix() {
        Person person = new Person();
        person.setFullName("FirstName",null,"LastName");

        assertEquals("FirstName LastName", person.getFullName());
    }

    @Test
    @DisplayName("getFullName for weird name")
    void getFullNameWorksWithWeirdNames() {
        Person person = new Person();
        person.setFullName("FirstName Second Name","really long affix","LastName ???");

        assertEquals("FirstName Second Name really long affix LastName ???", person.getFullName());
    }

    @Test
    @DisplayName("getFullName with empty affix fails")
    void getFullNameWithWrongInfoFails() {
        Person person = new Person();
        person.setFullName("FirstName","","LastName");

        assertNotEquals("FirstName LastName", person.getFullName());
    }

    @Test
    @DisplayName("Creation of new person assigns correct type")
    public void testConstructorAssignsCorrectType() {
        Person teacher = new Teacher();
        Person student = new Student();

        assertInstanceOf(Teacher.class, teacher, "Object must be a teacher");
        assertInstanceOf(Person.class, teacher, "Teacher is also a person(duh)");

        assertInstanceOf(Student.class, student, "This object is a student");
        assertInstanceOf(Person.class, student, "Student is also a person");

    }
}