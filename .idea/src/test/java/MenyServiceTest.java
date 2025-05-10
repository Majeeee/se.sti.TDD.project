package se.sti.TDD.project;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhetstester för MenyService – hanterar menyval och användarinteraktion.
 */
public class MenyServiceTest {

    // ══════════════════════════════════════════════════════════════════════
    // Standardfält och testförberedelser

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    private MenyService menyService;
    private MyATM atm;

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        menyService = new MenyService();
        atm = new MyATM();
        atm.loginTestUser(); // Simulerar en inloggad användare
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 0: Logga ut

    @Test
    void shouldLogoutWhenChoosingZero() throws Exception {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result); // Logga ut betyder fortsatt program
        assertTrue(testOut.toString().contains("utloggad"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 5: Avsluta ATM (bekräfta 'j')

    @Test
    void shouldExitWhenChoosingFiveAndConfirmingYes() throws Exception {
        String input = "5\nj\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertFalse(result); // Programmet avslutas
        assertTrue(testOut.toString().contains("Tack för att du använde ATM"));
    }

    // Menyval 5: Avsluta ATM (avbryt 'n')

    @Test
    void shouldNotExitWhenChoosingFiveAndConfirmingNo() throws Exception {
        String input = "5\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result); // Programmet fortsätter
        assertTrue(testOut.toString().contains("Avslutning avbröts"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 2: Sätta in pengar

    @Test
    void shouldDepositMoneyWhenChoosingTwo() throws Exception {
        String input = "2\n100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result);
        assertTrue(testOut.toString().contains("Insättning"));
        assertTrue(testOut.toString().contains("100,00"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 3: Ta ut pengar

    @Test
    void shouldWithdrawMoneyWhenChoosingThree() throws Exception {
        // Sätt in pengar först för att ha saldo att ta ut
        atm.depositCash(200);

        String input = "3\n100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result);
        assertTrue(testOut.toString().contains("Uttag"));
        assertTrue(testOut.toString().contains("-100,00"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Felaktigt menyval

    @Test
    void shouldHandleInvalidChoice() throws Exception {
        String input = "99\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result);
        assertTrue(testOut.toString().contains("Ogiltigt val"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 4: Visa kvitto

    @Test
    void shouldPrintReceiptWhenChoosingFour() throws Exception {
        atm.depositCash(50); // För att skapa transaktion

        String input = "4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result);
        assertTrue(testOut.toString().contains("KVITTO"));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyval 7: Rensa transaktioner med avbryt

    @Test
    void shouldAbortClearTransactionsIfNoConfirmed() throws Exception {
        String input = "7\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = menyService.visaMeny(atm);

        assertTrue(result);
        assertTrue(testOut.toString().contains("Rensning avbröts"));
    }
}
