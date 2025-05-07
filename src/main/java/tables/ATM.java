package tables;

public interface ATM {
    // ─────────────────────────────────────────────────────────────────────
    // Autentisering & sessionshantering
    void insertCard(String cardNumber);
    void enterPin(int pin);
    void ejectCard();

    // ─────────────────────────────────────────────────────────────────────
    // Transaktioner
    void withdrawCash(double amount);
    void depositCash(double amount);

    // ─────────────────────────────────────────────────────────────────────
    //  Saldo & kvitto
    double checkBalance();
    void showReceipt();
}
