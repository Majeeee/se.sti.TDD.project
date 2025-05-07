package se.sti.TDD.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyATMTest {

    private MyATM atm;

    @BeforeEach
    void setUp() {
        atm = new MyATM();
        atm.loginTestUser(); // ✨ Säkerställ inloggning för testanvändare
    }

    // ✅ Test: Saldot ska inte vara negativt
    @Test
    void checkBalanceShouldBeNonNegative() {
        double balance = atm.checkBalance();
        assertTrue(balance >= 0, "Saldo ska aldrig vara negativt.");
    }

    // ✅ Test: Insättning ska öka saldot
    @Test
    void depositCashShouldIncreaseBalance() {
        double before = atm.checkBalance(false);
        atm.depositCash(300);
        double after = atm.checkBalance(false);
        assertEquals(before + 300, after, 0.001, "Saldo bör öka med 300 SEK.");
    }

    // ✅ Test: Uttag ska minska saldot
    @Test
    void withdrawCashShouldDecreaseBalance() {
        atm.depositCash(500); // ✨ Initial insättning
        double before = atm.checkBalance(false);
        atm.withdrawCash(200);
        double after = atm.checkBalance(false);
        assertEquals(before - 200, after, 0.001, "Saldo bör minska med 200 SEK.");
    }

    // ✅ Test: Uttag som överskrider saldot ska inte genomföras
    @Test
    void withdrawMoreThanBalanceShouldFail() {
        double before = atm.checkBalance(false);
        atm.withdrawCash(before + 1000); // försök ta ut mer
        double after = atm.checkBalance(false);
        assertEquals(before, after, "Saldo ska vara oförändrat efter försök att ta ut för mycket.");
    }
}
