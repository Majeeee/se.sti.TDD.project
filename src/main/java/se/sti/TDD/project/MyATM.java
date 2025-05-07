package se.sti.TDD.project;

import se.sti.TDD.project.DBconnection.JDBCUtil;
import se.sti.TDD.project.DBconnection.Database;
import se.sti.TDD.project.Interfaces.ATM;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * MyATM är en implementation av ATM-interfacet.
 * Hanterar inloggning, saldo, insättning, uttag, kvitto och transaktionshistorik.
 */
public class MyATM implements ATM {

    // ══════════════════════════════════════════════════════════════════════
    // 🔧 Fält / Instansvariabler
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private int loggedInUserId = -1;
    private String loggedInUsername = "";

    // ══════════════════════════════════════════════════════════════════════
    // Inloggning och autentisering

    @Override
    public void insertCard(String cardNumber) {
        // Ej implementerat i denna simulator
    }

    @Override
    public void enterPin(int pin) {
        // Ej implementerat i denna simulator
    }

    @Override
    public void ejectCard() {
        System.out.println("Du är nu utloggad.");
        loggedInUserId = -1;
        loggedInUsername = "";
    }

    /**
     * Loggar in användare via databas.
     */
    public boolean login() {
        System.out.print("Användarnamn: ");
        String usernameInput = scanner.nextLine().trim().toLowerCase();

        System.out.print("Lösenord: ");
        String passwordInput = scanner.nextLine().trim();

        String sql = "SELECT USERID, USERNAME FROM USER WHERE LOWER(USERNAME) LIKE ? AND USERPASSWORD = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + usernameInput + "%");
            pstmt.setString(2, passwordInput);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    loggedInUserId = rs.getInt("USERID");
                    loggedInUsername = rs.getString("USERNAME");
                    System.out.println(" Inloggning lyckades. Välkommen, " + loggedInUsername + "!");
                    return true;
                } else {
                    System.out.println(" Fel användarnamn eller lösenord.");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid inloggning: " + e.getMessage());
        }

        return false;
    }

    // ══════════════════════════════════════════════════════════════════════
    // Transaktioner

    @Override
    public void withdrawCash(double amount) {
        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        double balance = checkBalance(false);
        if (amount > balance) {
            System.out.println("Otillräckligt saldo.");
            return;
        }

        saveTransaction("Uttag", -amount);
        skrivUtKvitto("Uttag", -amount, checkBalance(false));
    }

    @Override
    public void depositCash(double amount) {
        if (amount <= 0) {
            System.out.println("Beloppet måste vara större än 0.");
            return;
        }

        saveTransaction("Insättning", amount);
        skrivUtKvitto("Insättning", amount, checkBalance(false));
    }

    // ══════════════════════════════════════════════════════════════════════
    // Saldo & kvitto

    @Override
    public double checkBalance() {
        return checkBalance(true);
    }

    public double checkBalance(boolean printReceipt) {
        double balance = 0;
        String sql = "SELECT SUM(amount) as balance FROM transactions WHERE userId = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }

            if (printReceipt) {
                skrivUtKvitto("Saldo", 0, balance);
            }

        } catch (Exception e) {
            System.out.println(" Fel vid hämtning av saldo: " + e.getMessage());
        }

        return balance;
    }

    @Override
    public void showReceipt() {
        showLastTransaction();
    }

    // ══════════════════════════════════════════════════════════════════════
    // Meny och huvudloop

    public boolean ProjectChoice() throws Exception {
        System.out.println("\n────────────────────────────────────────");
        System.out.println(" Inloggad som: " + loggedInUsername);
        System.out.println("────────────────────────────────────────");
        System.out.println(" Huvudmeny:");
        System.out.println("0  Logga ut");
        System.out.println("1  Visa saldo");
        System.out.println("2  Sätt in");
        System.out.println("3  Ta ut");
        System.out.println("4  Visa senaste kvitto");
        System.out.println("5  Avsluta");
        System.out.println("6  Visa historik");
        System.out.println("7  Rensa transaktioner");
        System.out.println("8  Återskapa TRANSACTIONS-tabellen");
        System.out.println("────────────────────────────────────────");
        System.out.print("Välj ett alternativ (0–8): ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "0" -> ejectCard();
            case "1" -> checkBalance();
            case "2" -> {
                System.out.print("\n Belopp att sätta in: ");
                double amount = Double.parseDouble(scanner.nextLine());
                depositCash(amount);
            }
            case "3" -> {
                System.out.print("\n Belopp att ta ut: ");
                double amount = Double.parseDouble(scanner.nextLine());
                withdrawCash(amount);
            }
            case "4" -> showReceipt();
            case "5" -> {
                System.out.println("\n Tack för att du använde ATM-simulatorn!");
                running = false;
            }
            case "6" -> showAllTransactions();
            case "7" -> clearTransactions();
            case "8" -> {
                System.out.print("Är du säker på att du vill nollställa TRANSACTIONS-tabellen? (j/n): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("j")) {
                    Database.recreateTransactionsTable();
                } else {
                    System.out.println("Avbrutet.");
                }
            }
            default -> System.out.println("Ogiltigt val. Försök igen.");
        }

        pause();
        return running;
    }

    // ══════════════════════════════════════════════════════════════════════
    // Transaktionshantering

    private void skrivUtKvitto(String typ, double belopp, double saldo) {
        System.out.println("\n KVITTO");
        System.out.println("Typ: " + typ);
        System.out.printf("Belopp: %.2f SEK\n", belopp);
        System.out.printf("Tillgängligt saldo: %.2f SEK\n", saldo);
        System.out.println("Tid: " + LocalDateTime.now());
        System.out.println("────────────────────────────────────────");
    }

    private void saveTransaction(String type, double amount) {
        if (loggedInUserId == -1) {
            System.out.println("Ingen användare inloggad.");
            return;
        }

        String sql = "INSERT INTO transactions (userId, bankId, atmId, transactiontype, amount, currency, time) VALUES (?, 1, 1, ?, ?, 'SEK', datetime('now', 'localtime'))";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            System.out.println(" Fel vid sparande av transaktion: " + e.getMessage());
        }
    }

    private void showLastTransaction() {
        String sql = "SELECT transactiontype, amount, time FROM transactions WHERE userId = ? ORDER BY transactionId DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    skrivUtKvitto(rs.getString("transactiontype"), rs.getDouble("amount"), checkBalance(false));
                } else {
                    System.out.println("Inga transaktioner hittades.");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid hämtning av transaktion: " + e.getMessage());
        }
    }

    private void showAllTransactions() {
        String sql = "SELECT transactiontype, amount, time FROM transactions WHERE userId = ? ORDER BY transactionId";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n Historik:");
                while (rs.next()) {
                    System.out.printf("%s %.2f SEK (%s)\n",
                            rs.getString("transactiontype"),
                            rs.getDouble("amount"),
                            rs.getString("time"));
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid hämtning av historik: " + e.getMessage());
        }
    }

    private void clearTransactions() {
        String sql = "DELETE FROM transactions WHERE userId = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            int rows = pstmt.executeUpdate();
            JDBCUtil.commit(conn);
            System.out.println("🧹 " + rows + " transaktioner raderades.");

        } catch (SQLException e) {
            System.out.println(" Fel vid rensning: " + e.getMessage());
        }
    }

    private void pause() {
        System.out.print("\nTryck Enter för att fortsätta...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════
    //  Testfunktion för enhetstester

    /**
     * Metod för att sätta testanvändare (för JUnit-testning).
     */
    public void loginTestUser() {
        this.loggedInUserId = 1;
        this.loggedInUsername = "TestUser";
    }

    public boolean isRunning() {
        return false;
    }
}
