package se.sti.TDD.project;

import se.sti.TDD.project.Interfaces.User;

/**
 * MyUser representerar en användare med namn, ID och kontonummer.
 * Implementerar User-interfacet.
 */
public class MyUser implements User {

    // ══════════════════════════════════════════════════════════════════════
    // Fält
    private final String name;
    private final int userId;
    private final String accountNumber;

    // ══════════════════════════════════════════════════════════════════════
    // Konstruktor
    public MyUser(String name, int userId, String accountNumber) {
        this.name = name;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    // ══════════════════════════════════════════════════════════════════════
    // Getters (Interface-implementering)
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }
}
