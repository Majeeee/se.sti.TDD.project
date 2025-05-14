package se.sti.TDD.project;

import org.junit.jupiter.api.*;
import se.sti.TDD.project.DBconnection.JDBCUtil;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyATMTest {

    public MyATM atm;

    @BeforeEach
    public void setup() {
        atm = new MyATM();
    }

    @Test
    public void testInsertCard_ValidCredentials_ReturnsTrue() throws SQLException {
        // Arrange – du måste ha en användare i databasen med dessa uppgifter
        String username = "Maje";
        String password = "123";
        boolean result =  atm.insertCard(username, password);
        assertTrue(result, "Inloggning med giltiga uppgifter borde returnera true.");
    }

    @Test
    public void testInsertCard_InvalidCredentials_ReturnsFalse() throws SQLException {
        // Arrange – fel lösenord
        String username = "Maje";
        String password = "1";
        boolean result = atm.insertCard(username, password);
        assertFalse(result, "Inloggning med felaktiga uppgifter borde returnera false.");
    }


    @Test
    public void testWithdrawCashInsertsTransaction() throws SQLException {
        int testUserId = 1;
        int testBankId = 1;
        int testATMId = 1;
        double testAmount = 500.0;


        atm.withdrawCash(testUserId, testBankId, testATMId, testAmount);

        // Verifiera att raden finns i databasen
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM TRANSACTIONS WHERE USERID = ? AND BANKID = ? AND ATMID = ? AND AMOUNT = ? AND TRANSACTIONTYPE = '1'"
             )) {
            pstmt.setInt(1, testUserId);
            pstmt.setInt(2, testBankId);
            pstmt.setInt(3, testATMId);
            pstmt.setDouble(4, testAmount);

            ResultSet rs = pstmt.executeQuery();
            assertTrue(rs.next(), "Ingen withdraw transaktion hittades i databasen.");
        }
    }


    @Test
    public void testDepositCashInsertsTransaction() throws SQLException {
        int testUserId = 1;
        int testBankId = 1;
        int testATMId = 1;
        double testAmount = 500.0;

        // Anropa deposit-metoden
        atm.depositCash(testUserId, testBankId, testATMId, testAmount);

        // Verifiera att en korrekt transaktionspost skapades
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM TRANSACTIONS WHERE USERID = ? AND BANKID = ? AND ATMID = ? AND AMOUNT = ? AND TRANSACTIONTYPE = 2"
             )) {
            pstmt.setInt(1, testUserId);
            pstmt.setInt(2, testBankId);
            pstmt.setInt(3, testATMId);
            pstmt.setDouble(4, testAmount);

            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next(), "Ingen deposit transaktion hittades i databasen.");
            }
        }
    }


    @Test
    public void testCheckBalancePrintsCorrectBalance() throws SQLException {
        int testUserId = 2;

        //  Rensa befintliga transaktioner för testanvändaren
        try (Connection conn = JDBCUtil.getConnection()) {
            conn.createStatement().executeUpdate("DELETE FROM TRANSACTIONS WHERE USERID = " + testUserId);

            // testtransaktioner
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO TRANSACTIONS (USERID, BANKID, ATMID, TRANSACTIONTYPE, AMOUNT, CURRENCY,TIME) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            // Insättning (2): +1000
            pstmt.setInt(1, testUserId);
            pstmt.setInt(2, 1);
            pstmt.setInt(3, 1);
            pstmt.setString(4, "2"); // deposit
            pstmt.setDouble(5, 1000.0);
            pstmt.setString(6, "SEK");
            pstmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            pstmt.executeUpdate();

            // Uttag (1): -300
            pstmt.setString(4, "1"); // withdraw
            pstmt.setDouble(5, 300.0);
            pstmt.executeUpdate();

            conn.commit();
        }
        atm.checkBalance(testUserId);

    }






}













