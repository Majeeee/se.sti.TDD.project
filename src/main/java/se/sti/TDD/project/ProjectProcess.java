package se.sti.TDD.project;

import java.sql.SQLException;

public class ProjectProcess {

    private final MyATM myATM = new MyATM();

    MyUser myuser = new MyUser();


    public void printWelcometMenu() throws Exception {

// Loopen kommer att köras så länge 'running' är true
        System.out.println(ProjectConstants.RED + "                          Välkommen till ATM-simulatorn!\n" + ProjectConstants.RESET);

//        boolean loggedIn = false;
        String userName = null;

        while (!ProjectConstants.loginSuccessful) {
            System.out.println("Användare :");
            userName = ProjectInputValidater.getValidatedStringInput();

            System.out.println("Lössenord");
            String choicePassword = ProjectInputValidater.getValidatedStringInput();
//            new MyATM().insertCard(userName, choicePassword);
            ProjectConstants.loginSuccessful = myATM.insertCard(userName, choicePassword);


            if (!ProjectConstants.loginSuccessful) {
                System.out.println("Felaktigt användarnamn eller lösenord. Försök igen.");
            } else {
                System.out.println(ProjectConstants.RED + "Inloggning lyckades! Välkommen, " + userName + "." + ProjectConstants.RESET);
            }
        }
        int userId = myuser.getUserIdByUsername(userName);


        while (ProjectConstants.running) {
            System.out.println( "\n\n────────────-----------────────────\n" +
                                " Välkommen till ATM\n" +
                                "───────────----------─────────────\n" +
                                "Vilken del vill du gå, och ange att giltiga värden är nedanstående\n" +
                                "1. Visa saldo\n" +
                                "2. Sätt in pengar\n" +
                                "3. Ta ut pengar\n" +
                                "4. Visa kvitto(saldo)\n" +
                                "5. Avsluta\n" +
                                "────────────------------────────────\n" +
                                "Enter valet: ");

            int choice = ProjectInputValidater.getValidatedIntegerInput();
            projectchoice(choice, userId);


        }
    }

    public boolean projectchoice(int menuChoice, int userId) throws Exception {

        int bankId = myuser.getBankIdByUserId(userId);
        int ATMId = myuser.getATMIdByUserId(userId);

        switch (menuChoice) {
            case 1:
                myATM.checkBalance(userId);
                break;
            case 2:
                myATM.withdrawCash(userId, bankId, ATMId, ProjectInputValidater.getValidatedDoubleInput());
                break;
            case 3:
                myATM.depositCash(userId, bankId, ATMId, ProjectInputValidater.getValidatedDoubleInput());
                break;
            case 4:
                myATM.checkBalance(userId);
                break;
            case 5:
                System.out.println("Avslutar programmet. Tack för att du använde Mina sidor!");
                ProjectConstants.running = false;
                break;
            case 7:

        }

        return true;
    }
}
