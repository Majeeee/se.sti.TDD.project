package se.sti.TDD.project.DBconnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private static final String TEST_DB_PATH = "test_transactions.sqlite";

    // ══════════════════════════════════════════════════════════════════════
    // Körs före varje test – skapar en ren testdatabas
    @BeforeEach
    void setupTestDatabase() {
        File dbFile = new File(TEST_DB_PATH);
        if (dbFile.exists()) {
            dbFile.delete();  // Ta bort gammal testdatabas
        }
        Database.recreateTransactionsTable(); // Skapar ny tom TRANSACTIONS-tabell
    }

    // ══════════════════════════════════════════════════════════════════════
    // Test: Tabell TRANSACTIONS ska existera
    @Test
    void recreateTransactionsTableShouldCreateTable() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='TRANSACTIONS';");
            assertTrue(rs.next(), "TRANSACTIONS-tabellen bör finnas efter återställning");

        } catch (SQLException e) {
            fail("SQL-fel: " + e.getMessage());
        }
    }
}
