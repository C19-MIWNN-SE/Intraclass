//package nl.miwnn.ch19.DaMaGe.IntraClass.model;
//
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
///**
// * @author Danylo Dudar
// * <p>
// * Sunrise, Parabellum.
// */
//class IntraclassUserTest {
//    private IntraClassUser userWithId(Long id) {
//        IntraClassUser user = new IntraClassUser("gebruiker", "wachtwoord", "USER");
//        user.setId(id);
//        return user;
//    }
//
//    @Test
//    @DisplayName("equals should return true for two users with the same id")
//    void equalsShouldReturnTrueForSameId() {
//        IntraClassUser a = userWithId(1L);
//        IntraClassUser b = userWithId(1L);
//
//        assertEquals(a, b);
//    }
//
//    @Test
//    @DisplayName("equals should return false for two users with different ids")
//    void equalsShouldReturnFalseForDifferentIds() {
//        IntraClassUser a = userWithId(1L);
//        IntraClassUser b = userWithId(2L);
//
//        assertNotEquals(a, b);
//    }
//
//    @Test
//    @DisplayName("equals should return false when compared to null")
//    void equalsShouldReturnFalseForNull() {
//        IntraClassUser user = userWithId(1L);
//
//        assertNotEquals(null, user);
//    }
//
//    @Test
//    @DisplayName("equals should return false for a user without an id")
//    void equalsShouldReturnFalseForUnsavedUser() {
//        IntraClassUser a = new IntraClassUser("gebruiker", "wachtwoord", "USER");
//        IntraClassUser b = new IntraClassUser("gebruiker", "wachtwoord", "USER");
//
//        assertNotEquals(a, b);
//    }
//
//    @Test
//    @DisplayName("equals should return true when comparing an object to itself")
//    void equalsShouldReturnTrueForSameReference() {
//        IntraClassUser user = userWithId(1L);
//
//        assertEquals(user, user);
//    }
//}
