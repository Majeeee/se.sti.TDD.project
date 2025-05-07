package se.sti.TDD.project.Interfaces;

/**
 * Interface som beskriver en användare i systemet.
 */
public interface User {

    /**
     * Hämtar användarens fullständiga namn.
     * @return
     */
    String getName();

    /**
     * Hämtar användarens unika ID.
     * @return
     */
    int getUserId();

    /**
     * Hämtar användarens kontonummer.
     * @return
     */
    String getAccountNumber();
}
