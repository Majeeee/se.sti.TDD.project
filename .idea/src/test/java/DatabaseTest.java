package se.sti.TDD.project.DBconnection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    void recreateTransactionsTableShouldCreateTable() {
        Database.recreateTransactionsTable();

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='TRANSACTIONS';");
            assertTrue(rs.next(), "TRANSACTIONS-tabellen bör finnas efter återställning");

        } catch (SQLException e) {
            fail("Ett SQL-fel inträffade: " + e.getMessage());
        }
    }
}
