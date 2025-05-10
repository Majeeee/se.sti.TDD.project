package se.sti.TDD.project;

import java.util.Scanner;

/**
 * ProjectProcess styr körningen av ATM-simulatorn.
 * Sköter inloggning och visar huvudmeny i en loop.
 */
public class ProjectProcess {

    // ══════════════════════════════════════════════════════════════════════
    // 🔧 Instansvariabler

    private final Scanner scanner = new Scanner(System.in);
    private final MyATM myATM = new MyATM();
    private final MenyService menyService = new MenyService(myATM);

    // ══════════════════════════════════════════════════════════════════════
    // Startmetod

    /**
     * Startar ATM-simulatorn. Inloggning krävs innan menyn visas.
     */
    public void start() throws Exception {

        // ══════════════════════════════════════════════════════════════════════
        // Välkomstmeddelande
        System.out.println("\n Välkommen till ATM-simulatorn!");

        // ══════════════════════════════════════════════════════════════════════
        // Logga in användaren
        boolean loggedIn = myATM.login();

        // ══════════════════════════════════════════════════════════════════════
        // Visa meny om inloggning lyckades
        if (loggedIn) {
            boolean fortsätt = true;

            // Visa meny tills användaren loggar ut
            while (fortsätt) {
                fortsätt = menyService.visaMeny();  // ← styr loopen
            }

            // Avsluta programmet när användaren loggat ut
            System.out.println(" Tack för att du använde ATM-simulatorn.");
        } else {
            // Inloggning misslyckades
            System.out.println(" Programmet avslutas p.g.a. misslyckad inloggning.");
        }
    }
}
