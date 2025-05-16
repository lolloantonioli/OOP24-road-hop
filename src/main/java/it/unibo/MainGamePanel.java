package it.unibo;

import it.unibo.controller.GameState;
import it.unibo.controller.GameStateManager;

public class MainGamePanel {

    private final GameStateManager gameStateManager;

    public MainGamePanel() {
        // Initialize the game state manager
        this.gameStateManager = new GameStateManager();
        this.gameStateManager.setState(GameState.MENU);
    }
    
}
