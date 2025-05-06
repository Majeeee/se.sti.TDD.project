package se.sti.TDD.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyBankTest {

    @Test
    void depositShouldAddAmountToBalance() {
        MyBank bank = new MyBank();
        bank.deposit(200);
        assertEquals(200, bank.getBalance(), 0.01);
    }

    @Test
    void withdrawShouldSubtractAmountFromBalance() {
        MyBank bank = new MyBank();
        bank.deposit(300);
        bank.withdraw(100);
        assertEquals(200, bank.getBalance(), 0.01);
    }

    @Test
    void withdrawTooMuchShouldNotChangeBalance() {
        MyBank bank = new MyBank();
        bank.deposit(100);
        bank.withdraw(200);
        assertEquals(100, bank.getBalance(), 0.01);
    }
}
