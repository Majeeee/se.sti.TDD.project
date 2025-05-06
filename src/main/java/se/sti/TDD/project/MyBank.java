package se.sti.TDD.project;

import se.sti.TDD.project.Interfaces.Bank;

public class MyBank implements Bank {

    private double balance = 0.0;

    @Override
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
