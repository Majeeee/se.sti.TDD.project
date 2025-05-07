package se.sti.TDD.project;

import java.util.Scanner;

/**
 * ProjectProcess ansvarar för att starta och hantera hela ATM-sessionen.
 * Innehåller säkerhetsfunktioner som begränsar antal inloggningsförsök.
 */
public class ProjectProcess {

    // ══════════════════════════════════════════════════════════════════════
    // Instansvariabler
    private final Scanner scanner = new Scanner(System.in);
    private final MyATM myATM = new MyATM();

    // ══════════════════════════════════════════════════════════════════════
    // Startpunkt för ATM-processen
    public void start() throws Exception {
        System.out.println("📌 Välkommen till ATM-simulatorn");

        // Huvudloop – pågår tills användaren väljer att avsluta
        while (true) {

            // Max 3 försök att logga in
            int attempts = 0;
            final int MAX_ATTEMPTS = 3;

            while (!myATM.login()) {
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    System.out.println(" För många misslyckade försök. Programmet avslutas.");
                    return;
                }
                System.out.println(" Fel inloggning. Försök igen (" + (MAX_ATTEMPTS - attempts) + " försök kvar).\n");
            }

            // Kör användarmenyn tills användaren loggar ut eller avslutar
            while (myATM.ProjectChoice()) {

            }

            // Avsluta programmet om användaren valt alternativ 5
            if (!myATM.isRunning()) {
                System.out.println("👋 Program avslutat. Hejdå!");
                break;
            }

            // Fråga om ny inloggning
            System.out.print("\nVill du logga in igen? (j/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (!answer.equals("j")) {
                System.out.println("👋 Hejdå!");
                break;
            }
        }
    }
}
