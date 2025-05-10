package se.sti.TDD.project;

import se.sti.TDD.project.DBconnection.JDBCUtil;
import se.sti.TDD.project.DBconnection.Database;
import se.sti.TDD.project.Interfaces.ATM;

import java.sql.*;
import java.util.Scanner;

/**
 * MyATM är huvudklassen för ATM-simulatorn.
 * Den hanterar inloggning, kontohantering och transaktioner.
 */
public class MyATM implements ATM {

    // ══════════════════════════════════════════════════════════════════════
    // 🔧 Instansvariabler

    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private int loggedInUserId = -1;
    private String loggedInUsername = "";

    private final TransactionService transactionService = new TransactionService();
    private final AccountManager accountManager = new AccountManager();

    // ══════════════════════════════════════════════════════════════════════
    // ATM Interface-implementering

    @Override
    public void insertCard(String cardNumber) {}

    @Override
    public void enterPin(int pin) {}

    @Override
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

    @Override
    public void ejectCard() {
        System.out.println(" Du är nu utloggad.");
        loggedInUserId = -1;
        loggedInUsername = "";
    }

    @Override
    public void withdrawCash(double amount) {
        taUtPengar(amount);
    }

    @Override
    public void depositCash(double amount) {
        sättInPengar(amount);
    }

    @Override
    public double checkBalance() {
        return visaSaldo();
    }

    @Override
    public void showReceipt() {
        visaKvitto();
    }

    // ══════════════════════════════════════════════════════════════════════
    // Menyfunktioner

    public void sättInPengar(double amount) {
        if (amount <= 0) {
            System.out.println(" Beloppet måste vara större än 0.");
            return;
        }

        String konto = accountManager.getCurrentAccountType();
        transactionService.saveTransaction(loggedInUserId, "Insättning", amount, konto);
        transactionService.printReceipt("Insättning (" + konto + ")", amount, transactionService.getBalance(loggedInUserId, konto));
    }

    public void taUtPengar(double amount) {
        if (amount <= 0) {
            System.out.println(" Beloppet måste vara större än 0.");
            return;
        }

        String konto = accountManager.getCurrentAccountType();
        double saldo = transactionService.getBalance(loggedInUserId, konto);
        if (amount > saldo) {
            System.out.println(" Otillräckligt saldo.");
            return;
        }

        transactionService.saveTransaction(loggedInUserId, "Uttag", -amount, konto);
        transactionService.printReceipt("Uttag (" + konto + ")", -amount, transactionService.getBalance(loggedInUserId, konto));
    }

    public double visaSaldo() {
        String konto = accountManager.getCurrentAccountType();
        double saldo = transactionService.getBalance(loggedInUserId, konto);
        transactionService.printReceipt("Saldo (" + konto + ")", 0, saldo);
        return saldo;
    }

    public void visaKvitto() {
        String sql = "SELECT transactiontype, amount, time, accountType FROM transactions WHERE userId = ? ORDER BY transactionId DESC LIMIT 1";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    transactionService.printReceipt(
                            rs.getString("transactiontype") + " (" + rs.getString("accountType") + ")",
                            rs.getDouble("amount"),
                            transactionService.getBalance(loggedInUserId, rs.getString("accountType"))
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid hämtning av kvitto: " + e.getMessage());
        }
    }

    public void visaTransaktionshistorik() {
        String sql = "SELECT transactiontype, amount, time, accountType FROM transactions WHERE userId = ? ORDER BY transactionId";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%s %.2f SEK (%s) [%s]\n",
                            rs.getString("transactiontype"),
                            rs.getDouble("amount"),
                            rs.getString("time"),
                            rs.getString("accountType"));
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid historik: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Rensa transaktioner (alla konton)

    public void rensaTransaktioner() {
        String sql = "DELETE FROM transactions WHERE userId = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            int rader = pstmt.executeUpdate();
            JDBCUtil.commit(conn);
            System.out.println("🧹 Raderade " + rader + " transaktioner.");

        } catch (SQLException e) {
            System.out.println(" Fel vid radering: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Rensa transaktioner för ett konto

    public void rensaTransaktionerFörKonto(String kontoTyp) {
        String sql = "DELETE FROM transactions WHERE userId = ? AND accountType = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loggedInUserId);
            pstmt.setString(2, kontoTyp);
            int rader = pstmt.executeUpdate();
            JDBCUtil.commit(conn);
            System.out.println("🧹 Raderade " + rader + " transaktioner för kontotyp: " + kontoTyp);

        } catch (SQLException e) {
            System.out.println(" Fel vid radering: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Överföring mellan konton

    public void överförPengar(double belopp, String från, String till) {
        if (belopp <= 0) {
            System.out.println(" Beloppet måste vara större än 0.");
            return;
        }

        double saldo = transactionService.getBalance(loggedInUserId, från);
        if (saldo < belopp) {
            System.out.println(" Otillräckligt saldo på " + från + "-kontot.");
            return;
        }

        transactionService.saveTransaction(loggedInUserId, "Överföring ut", -belopp, från);
        transactionService.saveTransaction(loggedInUserId, "Överföring in", belopp, till);
        System.out.printf(" %.2f SEK överfört från %s till %s.\n", belopp, från, till);
    }

    // ══════════════════════════════════════════════════════════════════════
    // Hjälpmetoder

    public void pausa() {
        System.out.print("\nTryck Enter för att fortsätta...");
        scanner.nextLine();
    }

    public void återskapaTransaktionstabell() {
        Database.recreateTransactionsTable();
    }

    public void bytAktivtKonto(String val) {
        accountManager.switchAccount(val);
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getAccountType() {
        return accountManager.getCurrentAccountType();
    }

    public void loginTestUser() {
        loggedInUserId = 1;
        loggedInUsername = "TestUser";
    }
}
