package se.sti.TDD.project;

import java.util.Scanner;

public class ProjectInputValidater {
    public static Scanner scanner = new Scanner(System.in);

    public static int getValidatedIntegerInput() {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine()); // Läs in och försök omvandla till heltal
                return choice; // Returnera om det är giltigt
            } catch (NumberFormatException e) {
                System.out.println("Ogiltig inmatning! Ange ett giltigt nummer.");
            }
        }
    }

    public static String getValidatedStringInput() {
        while (true) {
            String input = scanner.nextLine().trim(); // Läs in text och ta bort eventuella blanksteg i början/slutet
            if (!input.isEmpty()) {
                return input; // Returnera om inmatningen inte är tom
            } else {
                System.out.println("Ogiltig inmatning! Texten får inte vara tom. Försök igen.");
            }
        }
    }

    public static String getUserId( String userName) {

        String sql = "SELECT u.USERID  FROM USER u " +
                "WHERE USERNAME = ?";







        while (true) {
            String input = scanner.nextLine().trim(); // Läs in text och ta bort eventuella blanksteg i början/slutet
            if (!input.isEmpty()) {
                return      input; // Returnera om inmatningen inte är tom
            } else {
                System.out.println("Ogiltig inmatning! Texten får inte vara tom. Försök igen.");
            }
        }
    }

//    public static double getValidatedDoubleInput() {
//        while (true) {
//            try {
//                System.out.print("Ange ett decimaltal: ");
//                double value = Double.parseDouble(scanner.nextLine()); // Läs in och omvandla till double
//                return value; // Returnera om det är giltigt
//            } catch (NumberFormatException e) {
//                System.out.println("Ogiltig inmatning! Ange ett giltigt decimaltal (exempel: 123.45).");
//            }
//        }
//    }


//
//    public static Date getValidatedDateInput() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Datumformat (exempel: 2023-12-07)
//        while (true) {
//            try {
////                System.out.print("Ange ett datum (format: yyyy-MM-dd): ");
//                System.out.print("Ange ett datum (format: yyyy-MM-dd): ");
//                String dateInput = scanner.nextLine(); // Läs in användarens inmatning
//                LocalDate localDate = LocalDate.parse(dateInput, formatter); // Omvandla strängen till LocalDate
//                return Date.valueOf(localDate); // Konvertera LocalDate till java.sql.Date
//            } catch (DateTimeParseException e) {
//                System.out.println("Ogiltig inmatning! Ange ett datum i formatet yyyy-MM-dd.");
//            }
//        }
    //  }

}
