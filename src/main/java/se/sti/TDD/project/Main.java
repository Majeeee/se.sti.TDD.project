package se.sti.TDD.project;

/**
 * Main är startpunkten för ATM-simulatorn.
 * Här initieras ProjectProcess som hanterar hela programflödet.
 */
public class Main {

    // ══════════════════════════════════════════════════════════════════════
    //  Huvudmetod – programstart
    public static void main(String[] args) throws Exception {

        //  Skapar och startar en ny ATM-process
        ProjectProcess projectProcess = new ProjectProcess();
        projectProcess.start();
    }
}
