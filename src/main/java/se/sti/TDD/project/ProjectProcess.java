package se.sti.TDD.project;

public class ProjectProcess {

    private final MyATM myATM = new MyATM();

    public void start() throws Exception {
        System.out.println("Välkommen till ATM-simulatorn!");

        while (myATM.ProjectChoice()) {
            // kör tills användaren väljer att avsluta
        }

        System.out.println("Programmet avslutades.");
    }
}
