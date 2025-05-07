package se.sti.TDD.project.Interfaces;

/**
 * Interface som definierar grundläggande funktionalitet för en ATM.
 * Implementeras av klasser som hanterar bankomatsystemet.
 */
public interface ATM {

    // ══════════════════════════════════════════════════════════════════════
    // Kortinmatning och autentisering

    /**
     * Simulerar att ett kort sätts in i bankomaten.
     * @param cardNumber
     */
    void insertCard(String cardNumber);

    /**
     * Autentiserar användaren med pinkod.
     * @param pin
     */
    void enterPin(int pin);

    /**
     * Loggar in användaren med användarnamn och lösenord.
     * @return true om inloggning lyckades, annars false
     */
    boolean login();

    /**
     * Avslutar sessionen och matar ut kortet.
     */
    void ejectCard();

    // ══════════════════════════════════════════════════════════════════════
    // 💰 Transaktioner

    /**
     * Tar ut pengar från användarens konto.
     * @param amount
     */
    void withdrawCash(double amount);

    /**
     * Sätter in pengar på användarens konto.
     * @param amount
     */
    void depositCash(double amount);

    /**
     * Kontrollerar det aktuella saldot.
     * @return
     */
    double checkBalance();

    /**
     * Visar det senaste kvittot (saldo, insättning eller uttag).
     */
    void showReceipt();
}
