package se.sti.TDD.project;

/**
 * AccountManager hanterar vilket konto som är aktivt för användaren.
 * Kontot kan vara "PRIVATE" eller "SPARA".
 */
public class AccountManager {

    // ══════════════════════════════════════════════════════════════════════
    // Instansvariabler

    private String currentAccountType = "PRIVATE";  // Standardkonto

    // ══════════════════════════════════════════════════════════════════════
    // Växla konto

    /**
     * Växlar kontotyp utifrån användarens inmatning.
     * "1" = SPARA, "2" = PRIVATE
     *
     * @param input Användarens menyval
     */
    public void switchAccount(String input) {
        if (input.equals("1")) {
            currentAccountType = "SPARA";
        } else if (input.equals("2")) {
            currentAccountType = "PRIVATE";
        } else {
            System.out.println(" Ogiltigt kontoalternativ. Kontotyp ändrades ej.");
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Hämta aktivt konto

    /**
     * Returnerar den aktuella kontotypen som är aktiv.
     *
     * @return "SPARA" eller "PRIVATE"
     */
    public String getCurrentAccountType() {
        return currentAccountType;
    }
}
