package se.sti.TDD.project;

import se.sti.TDD.project.DBconnection.JDBCUtil;
import se.sti.TDD.project.Interfaces.ATM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyATM implements ATM {


    /*Entligen ProjectLoginService det kan ändra metoden för kunna ta kortnummer*/
    @Override
    public Boolean insertCard(String username, String password) throws SQLException {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
//            boolean loginSuccessful = false;

            String sql = "SELECT u.USERNAME, u.USERPASSWORD , u.USERID FROM USER u " +
                    "WHERE USERNAME = ? AND USERPASSWORD = ?";

           try {
                conn = JDBCUtil.getConnection(); // Hämta databasanslutning
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Hämtar det hashade lösenordet från databasen
                    String storedPasswordHash = rs.getString("USERPASSWORD");

                    // Verifierar det inskrivna lösenordet mot det hashade lösenordet
                    if (password.equals(storedPasswordHash)) {
                        ProjectConstants.loginSuccessful = true; // Inloggningen lyckades
//                        System.out.println("Inloggning lyckades för användare: " + username);
                    } else {
                        System.out.println("Felaktigt lösenord för användare: " + username);
                    }
                } else {
                    System.out.println("Ingen användare hittades med användernamn: " + username);
                }
            } catch (SQLException e) {
                System.err.println("Ett fel uppstod vid exekvering av SELECT: " + e.getMessage());
                e.printStackTrace();
                throw e; // Vidarebefordra undantaget
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
                JDBCUtil.closeConnection(conn);
            }
    return ProjectConstants.loginSuccessful;
    }



    @Override
    public void enterPin(int pin, int userId) {

    }

    @Override
    public void withdrawCash(int userId, int bankId, int ATMId,double amount) {

    }

    @Override
    public void depositCash(int userId, int bankId, int ATMId, double amount) {

    }

    @Override
    public double checkBalance(int userId) {
        return 0;
    }

    @Override
    public void ejectCard() {

    }

}






//
//
//
//    private final Scanner scanner = new Scanner(System.in);
//    private boolean running = true;
//
//    @Override
//    public void insertCard(String cardNumber) {}
//
//    @Override
//    public void enterPin(int pin) {}
//
//    @Override
//    public void withdrawCash(double amount) {
////        if (amount <= 0) {
////            System.out.println("Beloppet måste vara större än 0.");
////            return;
////        }
////
////        double balance = checkBalance();
////        if (amount > balance) {
////            System.out.println("Otillräckligt saldo.");
////            return;
////        }
//
////        saveTransaction("Uttag", -amount);
////        System.out.println("Uttag genomfört.");
//    }
//
//    @Override
//    public void depositCash(double amount) {
////        if (amount <= 0) {
////            System.out.println("Beloppet måste vara större än 0.");
////            return ;
////        }
//
////        saveTransaction("Insättning", amount);
////        System.out.println("Insättning genomförd.");
//    }
//
//    @Override
//    public double checkBalance() {
////        double balance = 0;
////        String sql = "SELECT SUM(amount) as balance FROM transactions";
////
////        try (Connection conn = JDBCUtil.getConnection();
////             PreparedStatement pstmt = conn.prepareStatement(sql);
////             ResultSet rs = pstmt.executeQuery()) {
////
////            if (rs.next()) {
////                balance = rs.getDouble("balance");
////            }
////
////        } catch (Exception e) {
////            System.out.println("Fel vid hämtning av saldo: " + e.getMessage());
////        }
////
////        return balance;
//   }
//
//    @Override
//    public void ejectCard() {}
////
////    public boolean ProjectChoice() throws Exception {
////        System.out.println("\n\n────────────────────────");
////        System.out.println(" Välkommen till ATM");
////        System.out.println("────────────────────────");
////        System.out.println(" Huvudmeny:");
////        System.out.println("1  Visa saldo");
////        System.out.println("2  Sätt in");
////        System.out.println("3  Ta ut");
////        System.out.println("4  Visa kvitto");
////        System.out.println("5  Avsluta");
////        System.out.println("6  Visa historik från databas");
////        System.out.println("7  Rensa databasen");
////        System.out.println("8  Återskapa TRANSACTIONS-tabellen");
////        System.out.println("────────────────────────");
////        System.out.print("Välj ett alternativ (1–8): ");
////
////        String choice = scanner.nextLine().trim();
////
////        switch (choice) {
////            case "1" -> {
////                double balance = checkBalance();
////                System.out.printf("\n Ditt saldo är: %.2f SEK\n", balance);
////                pause();
////            }
////            case "2" -> {
////                System.out.print("\n Belopp att sätta in: ");
////                double amount = Double.parseDouble(scanner.nextLine());
////                depositCash(amount);
////                pause();
////            }
////            case "3" -> {
////                System.out.print("\n Belopp att ta ut: ");
////                double amount = Double.parseDouble(scanner.nextLine());
////                withdrawCash(amount);
////                pause();
////            }
////            case "4" -> {
////                showLastTransaction();
////                pause();
////            }
////            case "5" -> {
////                System.out.println("\n Tack för att du använde ATM-simulatorn!");
////                running = false;
////            }
////            case "6" -> {
////                showAllTransactions();
////                pause();
////            }
////            case "7" -> {
////                clearTransactions();
////                pause();
////            }
////            case "8" -> {
////                System.out.print("Är du säker på att du vill nollställa TRANSACTIONS-tabellen? (j/n): ");
////                String confirm = scanner.nextLine().trim().toLowerCase();
////                if (confirm.equals("j")) {
////                    Database.recreateTransactionsTable();
////                } else {
////                    System.out.println("Avbrutet.");
////                }
////                pause();
////            }
////            default -> {
////                System.out.println("Ogiltigt val. Försök igen.");
////                pause();
////            }
////        }
////
////        return running;
//   }
//
////    private void pause() {
////        System.out.print("\nTryck Enter för att fortsätta...");
////        scanner.nextLine();
////    }
//
////    private void saveTransaction(String type, double amount) {
////        String sql = "INSERT INTO transactions (userId, bankId, atmId, transactiontype, amount, currency, time) VALUES (1, 1, 1, ?, ?, 'SEK', datetime('now'))";
////
////        try (Connection conn = JDBCUtil.getConnection();
////             PreparedStatement pstmt = conn.prepareStatement(sql)) {
////
////            pstmt.setString(1, type);
////            pstmt.setDouble(2, amount);
////            pstmt.executeUpdate();
////            JDBCUtil.commit(conn);
////
////        } catch (SQLException e) {
////            System.out.println("Fel vid sparande av transaktion: " + e.getMessage());
////        }
////    }
//
////    private void showLastTransaction() {
////        String sql = "SELECT transactiontype, amount, time FROM transactions ORDER BY transactionId DESC LIMIT 1";
////
////        try (Connection conn = JDBCUtil.getConnection();
////             PreparedStatement pstmt = conn.prepareStatement(sql);
////             ResultSet rs = pstmt.executeQuery()) {
////
////            if (rs.next()) {
////                System.out.println("\nSenaste transaktion:");
////                System.out.printf("%s %.2f SEK (%s)\n", rs.getString("transactiontype"), rs.getDouble("amount"), rs.getString("time"));
////            } else {
////                System.out.println("Inga transaktioner hittades.");
////            }
////
////        } catch (SQLException e) {
////            System.out.println("Fel vid hämtning av transaktion: " + e.getMessage());
////        }
////    }
//
////    private void showAllTransactions() {
////        String sql = "SELECT transactiontype, amount, time FROM transactions ORDER BY transactionId";
////
////        try (Connection conn = JDBCUtil.getConnection();
////             PreparedStatement pstmt = conn.prepareStatement(sql);
////             ResultSet rs = pstmt.executeQuery()) {
////
////            System.out.println("\nHistorik:");
////            while (rs.next()) {
////                System.out.printf("%s %.2f SEK (%s)\n",
////                        rs.getString("transactiontype"),
////                        rs.getDouble("amount"),
////                        rs.getString("time"));
////            }
////
////        } catch (SQLException e) {
////            System.out.println("Fel vid hämtning av historik: " + e.getMessage());
////        }
////    }
//
////    private void clearTransactions() {
////        String sql = "DELETE FROM transactions";
////
////        try (Connection conn = JDBCUtil.getConnection();
////             PreparedStatement pstmt = conn.prepareStatement(sql)) {
////
////            int rows = pstmt.executeUpdate();
////            JDBCUtil.commit(conn);
////            System.out.println(rows + " transaktioner raderades.");
////
////        } catch (SQLException e) {
////            System.out.println("Fel vid rensning: " + e.getMessage());
////        }
////    }
//}
