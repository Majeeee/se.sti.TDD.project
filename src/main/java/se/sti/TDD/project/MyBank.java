package se.sti.TDD.project;

import se.sti.TDD.project.Interfaces.Bank;

/**
 * MyBank är en enkel implementation av Bank-interfacet.
 * Hanterar saldo, insättning och uttag.
 */
public class MyBank implements Bank {

    // ══════════════════════════════════════════════════════════════════════
    // Fält
    private double balance = 0.0;

    // ══════════════════════════════════════════════════════════════════════
    // Insättning
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            System.out.println(" Ogiltigt belopp att sätta in.");
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Uttag
    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println(" Ogiltigt uttag. Kontrollera saldo eller belopp.");
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    //  Hämta saldo
    @Override
    public double getBalance() {
        return balance;
    }
}
