package it.unibo;

import it.unibo.controller.MainControllerImpl;

/**
 * Entry point for the application.
 * This is a utility class and cannot be instantiated.
 */
public final class App {

    private App() {
    }

    /**
     * Main method to launch the application.
     * @param args the command line arguments (not used)
     */
    public static void main(final String[] args) {
        new MainControllerImpl().goToMenu();
    }
}
