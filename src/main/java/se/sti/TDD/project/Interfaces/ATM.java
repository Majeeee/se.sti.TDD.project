package se.sti.TDD.project.Interfaces;

public interface ATM {
    void insertCard(String cardNumber);  /*Simulerar att ett kort sätts in.*/
    void enterPin(int pin); /*Autentiserar användaren med pinkod.*/
    void withdrawCash(double amount);/*Tar ut pengar.*/
    void depositCash(double amount); /*Sätter in pengar.*/
    double checkBalance();
    void ejectCard(); /*Avslutar sessionen och matar ut kortet.*/
}