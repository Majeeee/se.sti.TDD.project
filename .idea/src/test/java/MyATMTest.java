package se.sti.TDD.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester för MyATM – kontrollerar att insättning, uttag och saldo fungerar som förväntat.
 */
public class MyATMTest {

    private MyATM atm;

    // ══════════════════════════════════════════════════════════════════════
    // Initierar ny testinstans före varje test
    @BeforeEach
    void setUp() {
        atm = new MyATM();
        atm.loginTestUser();
    }

    // ══════════════════════════════════════════════════════════════════════
    // INSÄTTNING

    @Test
    void depositCashShouldIncreaseBalance() {
        double before = atm.checkBalance();
        atm.depositCash(300);
        double after = atm.checkBalance();
        assertEquals(before + 300, after, 0.01, "Saldo bör öka med 300 SEK.");
    }

    @Test
    void depositNegativeAmountShouldNotAffectBalance() {
        double before = atm.checkBalance();
        atm.depositCash(-100);
        double after = atm.checkBalance();
        assertEquals(before, after, 0.01, "Negativa insättningar ska inte påverka saldo.");
    }

    // ══════════════════════════════════════════════════════════════════════
    // 💸 UTAG

    @Test
    void withdrawCashShouldDecreaseBalance() {
        atm.depositCash(200);
        double before = atm.checkBalance();
        atm.withdrawCash(100);
        double after = atm.checkBalance();
        assertEquals(before - 100, after, 0.01, "Saldo ska minska med 100 SEK vid uttag.");
    }

    @Test
    void withdrawTooMuchShouldNotChangeBalance() {
        atm.depositCash(100);                                         // Sätt in mindre än vad vi försöker ta ut
        double before = atm.checkBalance();
        atm.withdrawCash(1000);                                      // Försök ta ut för mycket
        double after = atm.checkBalance();
        assertEquals(before, after, 0.01, "Saldo ska inte ändras om man försöker ta ut mer än man har.");
    }

    @Test
    void withdrawNegativeAmountShouldNotAffectBalance() {
        atm.depositCash(200);
        double before = atm.checkBalance();
        atm.withdrawCash(-50);
        double after = atm.checkBalance();
        assertEquals(before, after, 0.01, "Negativt uttag ska ignoreras.");
    }
}
