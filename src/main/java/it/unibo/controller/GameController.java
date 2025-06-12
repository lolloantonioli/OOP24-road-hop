package it.unibo.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private final GameEngine gameEngine;

    public GameController(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameEngine.getState().getName().equals("ON_GAME")) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                gameEngine.setState(new PauseState());
            }
            // altri comandi di gioco...
        }
        // In pausa, input gestito dai pulsanti del panel
    }
}
