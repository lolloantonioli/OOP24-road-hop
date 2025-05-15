package it.unibo.controller;

public class GameStateManager {
    private GameState currentState;
    
    /**
     * Gets the current state of the game.
     * @return The current state
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the game.
     * @param state The new state
     */
    public void setState(GameState state) {
        this.currentState = state;
    }

    /**
     * Starts a new game.
     */
    public void startGame() {
        currentState = GameState.PLAYING;
        // Initialize game logic here
    }

    /**
     * Ends the current game.
     * @param score The final score
     */
    public void endGame(int score) {
        currentState = GameState.GAME_OVER;
    }

    /**
     * Returns to the main menu.
     */
    public void returnToMenu() {
        currentState = GameState.MENU;
    }

    /**
     * Opens the shop.
     */
    public void openShop() {
        currentState = GameState.SHOP;
    }

    /**
     * Interfaces to Initialize game logic
     */
    public interface GameInitializer {
        void initGame();
    }

    /**
     * Interfaces to Initialize shop logic
     */
    public interface ShopInitializer {
        void initShop();
    }
}
