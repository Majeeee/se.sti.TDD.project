package se.sti.TDD.project.Interfaces;

import java.sql.SQLException;

public interface ATM {
    Boolean insertCard (String userName, String password) throws SQLException;  /*Simulerar att användaren loggas in.*/
    void enterPin(int pin, int userId); /*Autentiserar användaren med pinkod.*/
    void withdrawCash(int userId, int bankId, int ATMId,double amount) throws SQLException;/*Tar ut pengar.*/
    void depositCash(int userId, int bankId, int ATMId,double amount) throws SQLException; /*Sätter in pengar.*/
    void checkBalance(int userId) throws SQLException;
    void ejectCard(); /*Avslutar sessionen och matar ut kortet.*/
}