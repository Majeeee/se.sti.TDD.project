package se.sti.TDD.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhetstester för MyBank-klassen.
 * Testar insättning, uttag och saldokontroll.
 */
class MyBankTest {

    // ══════════════════════════════════════════════════════════════════════
    // Test: Insättning lägger till belopp
    @Test
    void depositShouldAddAmountToBalance() {
        MyBank bank = new MyBank();
        bank.deposit(200);
        assertEquals(200, bank.getBalance(), 0.01);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  Test: Uttag drar av belopp
    @Test
    void withdrawShouldSubtractAmountFromBalance() {
        MyBank bank = new MyBank();
        bank.deposit(300);
        bank.withdraw(100);
        assertEquals(200, bank.getBalance(), 0.01);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  Test: Uttag större än saldo ska inte påverka
    @Test
    void withdrawTooMuchShouldNotChangeBalance() {
        MyBank bank = new MyBank();
        bank.deposit(100);
        bank.withdraw(200);
        assertEquals(100, bank.getBalance(), 0.01);
    }

    // ══════════════════════════════════════════════════════════════════════
    // Test: Negativ insättning ska inte påverka saldot
    @Test
    void negativeDepositShouldNotChangeBalance() {
        MyBank bank = new MyBank();
        bank.deposit(-500);
        assertEquals(0, bank.getBalance(), 0.01);
    }
}
