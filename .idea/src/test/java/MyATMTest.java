package se.sti.TDD.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyATMTest {

    private MyATM atm;

    @BeforeEach
    void setUp() {
        atm = new MyATM();
    }

    @Test
    void checkBalanceShouldBeNonNegative() {
        double balance = atm.checkBalance();
        assertTrue(balance >= 0, "Saldo ska aldrig vara negativt.");
    }

    @Test
    void depositCashShouldIncreaseBalance() {
        double before = atm.checkBalance();
        atm.depositCash(100);
        double after = atm.checkBalance();
        assertTrue(after >= before + 100, "Saldo bör öka med minst det insatta beloppet.");
    }

    @Test
    void withdrawCashShouldDecreaseBalance() {
        atm.depositCash(200); // säkerställ att vi har tillräckligt med pengar
        double before = atm.checkBalance();
        atm.withdrawCash(100);
        double after = atm.checkBalance();
        assertTrue(before - after >= 100, "Uttag bör minska saldot med minst uttaget belopp.");
    }

    @Test
    void withdrawMoreThanBalanceShouldFail() {
        double before = atm.checkBalance();
        atm.withdrawCash(before + 100); // försök ta ut mer än tillgängligt
        double after = atm.checkBalance();
        assertEquals(before, after, "Uttag som överskrider saldot bör inte genomföras.");
    }
}
