package se.sti.TDD.project.DBconnection;

import java.sql.*;

public class JDBCUtil {

    // Välj databas beroende på om testmiljö är aktiv
    private static final String PROD_URL = "jdbc:sqlite:identifier.sqlite";
    private static final String TEST_URL = "jdbc:sqlite:test_transactions.sqlite";

    private static Connection connection;

    // Hämta en anslutning till rätt databas
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            boolean isTestEnv = Boolean.getBoolean("test.env");
            String url = isTestEnv ? TEST_URL : PROD_URL;

            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
            System.out.println("Databasen är ansluten: " + (isTestEnv ? "TEST" : "PROD"));
        }
        return connection;
    }

    // Stäng anslutningen
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Databasen är frånkopplad.");
            }
        } catch (SQLException e) {
            System.out.println("Fel vid frånkoppling: " + e.getMessage());
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
