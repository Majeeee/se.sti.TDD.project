package tables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhetstester för Transactions-klassen.
 * Testar fältens getters/setters samt toString().
 */
public class TransactionsTest {

    // ══════════════════════════════════════════════════════════════════════
    // Test: Setters och getters fungerar korrekt
    @Test
    void testSettersAndGetters() {
        Transactions t = new Transactions();

        t.setTransactionId(1);
        t.setUserId(2);
        t.setBankId(3);
        t.setATMId(4);
        t.setTransactionsType(5);
        t.setAmount(999.99);
        t.setCurrency("SEK");
        t.setTime("2025-05-07 12:00:00");

        assertEquals(1, t.getTransactionId());
        assertEquals(2, t.getUserId());
        assertEquals(3, t.getBankId());
        assertEquals(4, t.getATMId());
        assertEquals(5, t.getTransactionsType());
        assertEquals(999.99, t.getAmount(), 0.001);
        assertEquals("SEK", t.getCurrency());
        assertEquals("2025-05-07 12:00:00", t.getTime());
    }

    // ══════════════════════════════════════════════════════════════════════
    // Test: toString innehåller alla fältvärden
    @Test
    void testToStringContainsAllFields() {
        Transactions t = new Transactions();
        t.setTransactionId(1);
        t.setUserId(2);
        t.setBankId(3);
        t.setATMId(4);
        t.setTransactionsType(5);
        t.setAmount(100);
        t.setCurrency("SEK");
        t.setTime("2025-05-07 12:00:00");

        String output = t.toString();

        assertTrue(output.contains("transactionId=1"));
        assertTrue(output.contains("userId=2"));
        assertTrue(output.contains("bankId=3"));
        assertTrue(output.contains("ATMId=4"));
        assertTrue(output.contains("transactionsType=5"));
        assertTrue(output.contains("amount=100.0"));
        assertTrue(output.contains("currency='SEK'"));
        assertTrue(output.contains("time='2025-05-07 12:00:00'"));
    }
}
