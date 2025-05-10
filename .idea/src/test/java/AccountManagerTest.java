package se.sti.TDD.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester för AccountManager - kontobyte och återhämtning av aktuell kontotyp.
 */
public class AccountManagerTest {

    // ══════════════════════════════════════════════════════════════════════
    // Testförberedelse

    private AccountManager accountManager;

    @BeforeEach
    void setup() {
        accountManager = new AccountManager();
    }

    // ══════════════════════════════════════════════════════════════════════
    // Tester för kontoaktivering

    @Test
    void defaultAccountShouldBePrivate() {
        assertEquals("PRIVATE", accountManager.getCurrentAccountType(),
                "Standardkonto ska vara PRIVATE vid uppstart.");
    }

    @Test
    void shouldSwitchToSavingAccountWhenChoosingOne() {
        accountManager.switchAccount("1");
        assertEquals("SPARA", accountManager.getCurrentAccountType(),
                "Kontotyp ska ändras till SPARA vid val '1'.");
    }

    @Test
    void shouldSwitchToPrivateAccountWhenChoosingTwo() {
        accountManager.switchAccount("1");  // först till SPARA
        accountManager.switchAccount("2");  // sen tillbaka
        assertEquals("PRIVATE", accountManager.getCurrentAccountType(),
                "Kontotyp ska ändras tillbaka till PRIVATE vid val '2'.");
    }

    @Test
    void shouldNotChangeAccountOnInvalidInput() {
        accountManager.switchAccount("hej");
        assertEquals("PRIVATE", accountManager.getCurrentAccountType(),
                "Ogiltig inmatning ska inte ändra kontotyp.");
    }
}
