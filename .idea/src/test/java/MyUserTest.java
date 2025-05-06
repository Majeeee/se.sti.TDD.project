package se.sti.TDD.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyUserTest {

    @Test
    void getNameShouldReturnEmptyString() {
        MyUser user = new MyUser();
        assertEquals("", user.getName());
    }

    @Test
    void getUserIdShouldReturnZero() {
        MyUser user = new MyUser();
        assertEquals(0, user.getUserId());
    }

    @Test
    void getAccountNumberShouldReturnEmptyString() {
        MyUser user = new MyUser();
        assertEquals("", user.getAccountNumber());
    }
}
