package se.sti.TDD.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhetstester för MyUser-klassen.
 * Verifierar att alla getter-metoder returnerar korrekt data.
 */
public class MyUserTest {

    // ══════════════════════════════════════════════════════════════════════
    // 🧪 Test: Namn ska returneras korrekt
    @Test
    void getNameShouldReturnExpectedString() {
        MyUser user = new MyUser("Alice", 1, "123-456");
        assertEquals("Alice", user.getName());
    }

    // ══════════════════════════════════════════════════════════════════════
    // 🧪 Test: Användar-ID ska returneras korrekt
    @Test
    void getUserIdShouldReturnExpectedValue() {
        MyUser user = new MyUser("Bob", 42, "987-654");
        assertEquals(42, user.getUserId());
    }

    // ══════════════════════════════════════════════════════════════════════
    // 🧪 Test: Kontonummer ska returneras korrekt
    @Test
    void getAccountNumberShouldReturnExpectedValue() {
        MyUser user = new MyUser("Charlie", 99, "ACC-001");
        assertEquals("ACC-001", user.getAccountNumber());
    }
}
