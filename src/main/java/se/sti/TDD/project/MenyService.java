// ══════════════════════════════════════════════════════════════════════
// PAKET & IMPORTER

package se.sti.TDD.project;

import java.util.Scanner;

// ══════════════════════════════════════════════════════════════════════
// KLASS: MenyService
/**
 * MenyService ansvarar för att visa huvudmenyn och hantera användarens val.
 * Returnerar false om användaren väljer att logga ut.
 */
public class MenyService {

    // ══════════════════════════════════════════════════════════════════════
    // INSTANSVARIABLER

    private final Scanner scanner = new Scanner(System.in);
    private final MyATM myATM;

    // ══════════════════════════════════════════════════════════════════════
    // KONSTRUKTOR

    public MenyService(MyATM myATM) {
        this.myATM = myATM;
    }

    // ══════════════════════════════════════════════════════════════════════
    // HUVUDMENY
    /**
     * Visar huvudmenyn för ATM och utför valt alternativ.
     * @return
     */
    public boolean visaMeny() {
        // ──────────────────────────────────────────────────────────────────────
        // MENYHEADER
        System.out.println("\n══════════════════════════════════════");
        System.out.println("Välkommen, " + myATM.getLoggedInUsername());
        System.out.println("Aktivt konto: " + myATM.getAccountType());
        System.out.println("══════════════════════════════════════");
        System.out.println("1. Sätt in pengar");
        System.out.println("2. Ta ut pengar");
        System.out.println("3. Visa saldo");
        System.out.println("4. Visa kvitto");
        System.out.println("5. Visa transaktionshistorik");
        System.out.println("6. Byt aktivt konto");
        System.out.println("7. Rensa transaktioner");
        System.out.println("8. Överför pengar mellan konton");
        System.out.println("9. Logga ut");
        System.out.println("10. Återskapa TRANSACTIONS-tabellen");
        System.out.print("\nVälj ett alternativ: ");
        String val = scanner.nextLine().trim();

        // ──────────────────────────────────────────────────────────────────────
        // MENYVALSHANTERING
        switch (val) {
            case "1" -> {
                // Insättning
                System.out.print(" Belopp att sätta in: ");
                double insättning = Double.parseDouble(scanner.nextLine());
                myATM.sättInPengar(insättning);
            }
            case "2" -> {
                // Uttag
                System.out.print(" Belopp att ta ut: ");
                double uttag = Double.parseDouble(scanner.nextLine());
                myATM.taUtPengar(uttag);
            }
            case "3" -> {
                // Visa saldo
                myATM.visaSaldo();
            }
            case "4" -> {
                // Visa kvitto
                myATM.visaKvitto();
            }
            case "5" -> {
                // Visa historik
                myATM.visaTransaktionshistorik();
            }
            case "6" -> {
                // Byta konto
                System.out.print("Välj konto (1=SPARA, 2=PRIVATE): ");
                String kontoVal = scanner.nextLine().trim();
                myATM.bytAktivtKonto(kontoVal);
            }
            case "7" -> {
                // Rensa transaktioner
                System.out.print(" Välj konto att rensa (1=SPARA, 2=PRIVATE, annan för att avbryta): ");
                String typ = scanner.nextLine().trim();
                switch (typ) {
                    case "1" -> myATM.rensaTransaktionerFörKonto("SPARA");
                    case "2" -> myATM.rensaTransaktionerFörKonto("PRIVATE");
                    default -> System.out.println(" Rensning avbröts.");
                }
            }
            case "8" -> {
                // Överför pengar
                System.out.print("Från konto (SPARA/PRIVATE): ");
                String från = scanner.nextLine().trim().toUpperCase();
                System.out.print("Till konto (SPARA/PRIVATE): ");
                String till = scanner.nextLine().trim().toUpperCase();
                System.out.print("Belopp att överföra: ");
                double belopp = Double.parseDouble(scanner.nextLine());
                myATM.överförPengar(belopp, från, till);
            }
            case "9" -> {
                // Logga ut
                myATM.ejectCard();
                return false;
            }
            case "10" -> {
                // Återskapa transaktionstabell
                myATM.återskapaTransaktionstabell();
            }
            default -> {
                // Ogiltigt val
                System.out.println(" Ogiltigt alternativ.");
            }
        }

        // ──────────────────────────────────────────────────────────────────────
        // Paus före återgång till meny
        myATM.pausa();
        return true;
    }
}
