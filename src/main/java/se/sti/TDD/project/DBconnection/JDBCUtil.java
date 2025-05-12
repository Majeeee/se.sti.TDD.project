package se.sti.TDD.project.DBconnection;

import java.sql.*;

public class JDBCUtil {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";
    private static Connection connection;

    // Ansluter till SQLite
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            connection.setAutoCommit(false);
            System.out.println("Databasen är ansluten.");
        }
        return connection;
    }

    // Stänger anslutningen
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            //    System.out.println("Databasen är frånkopplad.");
            }
        } catch (SQLException e) {
            System.out.println("Fel vid frånkoppling: " + e.getMessage());
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
