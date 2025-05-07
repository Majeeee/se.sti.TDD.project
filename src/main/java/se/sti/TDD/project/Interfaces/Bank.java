package se.sti.TDD.project.Interfaces;

/**
 * Interface som definierar grundläggande banktjänster kopplade till en användares konto.
 */
public interface Bank {

    /**
     * Sätter in pengar på bankkontot.
     * @param amount
     */
    void deposit(double amount);

    /**
     * Tar ut pengar från bankkontot.
     * @param amount
     */
    void withdraw(double amount);

    /**
     * Hämtar aktuellt saldo för kontot.
     * @return
     */
    double getBalance();
}
