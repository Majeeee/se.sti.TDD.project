package se.sti.TDD.project.DBconnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public static void recreateTransactionsTable() {
        String dropTable = "DROP TABLE IF EXISTS TRANSACTIONS;";

        String createTable = """
            CREATE TABLE TRANSACTIONS (
                TRANSACTIONID INTEGER PRIMARY KEY AUTOINCREMENT,
                USERID INTEGER NOT NULL,
                BANKID INTEGER NOT NULL,
                ATMID INTEGER NOT NULL,
                TRANSACTIONTYPE TEXT NOT NULL,
                AMOUNT REAL NOT NULL,
                CURRENCY TEXT NOT NULL,
                TIME TEXT NOT NULL,
                FOREIGN KEY (USERID) REFERENCES USER(USERID),
                FOREIGN KEY (BANKID) REFERENCES BANK(BANKID),
                FOREIGN KEY (ATMID) REFERENCES ATM(ATMID)
            );
            """;

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(dropTable);
            stmt.executeUpdate(createTable);
            JDBCUtil.commit(conn);
            System.out.println("✅ TRANSACTIONS-tabellen återskapades utan UNIQUE-begränsning.");

        } catch (SQLException e) {
            JDBCUtil.rollback(null);
            System.out.println("❌ Fel vid återställning av tabell: " + e.getMessage());
        }
    }
}
