package se.sti.TDD.project;

import se.sti.TDD.project.DBconnection.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * TransactionService ansvarar för att hantera transaktioner:
 * - Spara till databasen
 * - Skriva ut kvitto
 * - Hämta saldo för ett konto
 */
public class TransactionService {

    // ══════════════════════════════════════════════════════════════════════
    // Spara transaktion i databasen

    /**
     * Sparar en transaktion i TRANSACTIONS-tabellen i databasen.
     *
     * @param userId                                                        Användarens ID
     * @param type                                                          Typ av transaktion (Insättning, Uttag, etc.)
     * @param amount                                                        Beloppet (positivt eller negativt)
     * @param accountType                                                   Konto: "SPARA" eller "PRIVATE"
     */
    public void saveTransaction(int userId, String type, double amount, String accountType) {
        String sql = """
                INSERT INTO transactions (userId, bankId, atmId, transactiontype, amount, currency, accountType, time)
                VALUES (?, 1, 1, ?, ?, 'SEK', ?, datetime('now', 'localtime'))
                """;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, accountType);
            pstmt.executeUpdate();
            JDBCUtil.commit(conn);

        } catch (SQLException e) {
            System.out.println(" Fel vid sparande av transaktion: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    // Skriv ut kvitto

    /**
     * Skriver ut kvitto med information om transaktion och saldo.
     *
     * @param typ                                                                          Transaktionstyp
     * @param belopp                                                                       Beloppet
     * @param saldo                                                                        Nuvarande saldo
     */
    public void printReceipt(String typ, double belopp, double saldo) {
        System.out.println("\n KVITTO");
        System.out.println("Typ: " + typ);
        System.out.printf("Belopp: %.2f SEK\n", belopp);
        System.out.printf("Tillgängligt saldo: %.2f SEK\n", saldo);
        System.out.println("Tid: " + LocalDateTime.now());
        System.out.println("────────────────────────────────────────");
    }

    // ══════════════════════════════════════════════════════════════════════
    // Hämta saldo

    /**
     * Returnerar det totala saldot för ett användarkonto och kontotyp.
     *
     * @param userId                                                                        Användarens ID
     * @param accountType                                                                   Kontotyp: SPARA / PRIVATE
     * @return                                                                              Saldot (kan vara 0)
     */
    public double getBalance(int userId, String accountType) {
        double balance = 0;
        String sql = "SELECT SUM(amount) FROM transactions WHERE userId = ? AND accountType = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, accountType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(" Fel vid saldohämtning: " + e.getMessage());
        }

        return balance;
    }
}
