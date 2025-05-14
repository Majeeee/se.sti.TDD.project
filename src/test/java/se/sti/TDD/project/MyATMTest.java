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

        // Kör metoden
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
            assertTrue(rs.next(), "Ingen transaktion hittades i databasen.");
        }
    }














}
