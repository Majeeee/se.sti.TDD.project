package se.sti.TDD.project;

import se.sti.TDD.project.DBconnection.JDBCUtil;
import se.sti.TDD.project.Interfaces.User;

import java.sql.*;

public class MyUser implements User {


    @Override
    public int getUserIdByUsername(String username) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT USERID FROM USER WHERE USERNAME = ?";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("USERID"); // Returnera hittat ID
            } else {
                System.out.println("Ingen användare hittades med användarnamn: " + username);
                return -1; // -1 signalerar att inget ID hittades
            }

        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av användar-ID: " + e.getMessage());
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Override
    public int getBankIdByUserId(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT b.BANKID " +
                "FROM USER u,BANK b" +
                " WHERE  u.BANKID = b.BANKID" +
                " AND USERID = ?";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("BANKID"); // Returnera hittat ID
            } else {
                System.out.println("Ingen Bank hittades med användarnamn: " + userId);
                return -1; // -1 signalerar att inget ID hittades
            }

        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av Bank-ID: " + e.getMessage());
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }


    @Override
    public int getATMIdByUserId(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT DISTINCT a.ATMID \n" +
                     " FROM TRANSACTIONS t\n" +
                     " JOIN ATM a ON t.ATMID = a.ATMID\n" +
                     " WHERE t.USERID = ?";

        try {
            conn = JDBCUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ATMID"); // Returnera hittat ID
            } else {
                System.out.println("Ingen användare hittades med användarnamn: " + userId);
                return -1; // -1 signalerar att inget ID hittades
            }

        } catch (SQLException e) {
            System.err.println("Fel vid hämtning av ATM-ID: " + e.getMessage());
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            JDBCUtil.closeConnection(conn);
        }
    }

}
