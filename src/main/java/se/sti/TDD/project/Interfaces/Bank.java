package se.sti.TDD.project.Interfaces;

import java.sql.SQLException;

public interface Bank {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
}
