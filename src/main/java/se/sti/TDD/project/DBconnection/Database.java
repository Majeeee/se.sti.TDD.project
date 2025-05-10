package se.sti.TDD.project.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database ansvarar för att hantera databastabeller.
 * Denna klass återskapar TRANSACTIONS-tabellen med rätt kolumner.
 */
public class Database {

    // ══════════════════════════════════════════════════════════════════════
    // 🛠 Återskapar TRANSACTIONS-tabellen i databasen

    public static void recreateTransactionsTable() {
        // SQL för att ta bort tabellen om den redan finns
        String dropTable = "DROP TABLE IF EXISTS TRANSACTIONS;";

        // SQL för att skapa tabellen med alla nödvändiga kolumner
        String createTable = """
            CREATE TABLE TRANSACTIONS (
                TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,
                USERID INTEGER NOT NULL,
                BANKID INTEGER NOT NULL,
                ATMID INTEGER NOT NULL,
                TRANSACTIONTYPE TEXT NOT NULL,
                AMOUNT REAL NOT NULL,
                CURRENCY TEXT NOT NULL,
                ACCOUNTTYPE TEXT NOT NULL, 
                TIME TEXT NOT NULL,
                FOREIGN KEY (USERID) REFERENCES USER(USERID),
                FOREIGN KEY (BANKID) REFERENCES BANK(BANKID),
                FOREIGN KEY (ATMID) REFERENCES ATM(ATMID)
            );
            """;

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Släng befintlig tabell och skapa ny
            stmt.executeUpdate(dropTable);
            stmt.executeUpdate(createTable);

            // Bekräfta ändringarna
            JDBCUtil.commit(conn);
            System.out.println(" TRANSACTIONS-tabellen återskapades.");

        } catch (SQLException e) {
            // Ångra om något går fel
            JDBCUtil.rollback(null);
            System.out.println(" Fel vid återställning av tabell: " + e.getMessage());
        }
    }
}
