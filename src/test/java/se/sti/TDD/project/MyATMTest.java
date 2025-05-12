package se.sti.TDD.project;

import org.junit.jupiter.api.*;
import se.sti.TDD.project.MyATM;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class MyATMTest {

    public MyATM atm;

    @BeforeEach
    public void setup() {
        atm = new MyATM();
    }

    @Test
    public void testInsertCard_ValidCredentials_ReturnsTrue() throws SQLException {
        // Arrange – du måste ha en användare i databasen med dessa uppgifter
        String username = "Moise";
        String password = "1234";
        boolean result =  atm.insertCard(username, password);
        assertTrue(result, "Inloggning med giltiga uppgifter borde returnera true.");
    }

    @Test
    public void testInsertCard_InvalidCredentials_ReturnsFalse() throws SQLException {
        // Arrange – fel lösenord
        String username = "Maje";
        String password = "1";
        boolean result = atm.insertCard(username, password);
        assertFalse(result, "Inloggning med felaktiga uppgifter borde returnera false.");
    }
}
