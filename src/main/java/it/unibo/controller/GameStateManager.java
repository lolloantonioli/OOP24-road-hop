package it.unibo.controller;

public class GameStateManager {
   
    private static GameStateManager istance;
   
    private GameState currentState;
    private final GameInitializer gameInitializer;
    private final ShopInitializer shopInitializer;

    public GameStateManager(GameInitializer gameInitializer, ShopInitializer shopInitializer) {
        this.currentState = GameState.MENU;
        this.gameInitializer = gameInitializer;
        this.shopInitializer = shopInitializer;
        
        //Set this as the singleton instance
        if (istance == null) {
            istance = this;
        }
    }

    /**
     * Gets the singleton instance of GameStateManager.
     * @return The singleton instance
     */
    public static GameStateManager getInstance() {
        return istance;
    }
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
        switch (state) {
            case PLAYING:
                if (currentState != GameState.PLAYING) {
                    gameInitializer.initGame();
                }
                break;
            case SHOP:
                if (currentState != GameState.SHOP) {
                    shopInitializer.initShop();
                }
                break;
            case MENU:
                //implement menu logic
                break;
            case GAME_OVER:
                //implement game over logic
                break;
        }
    }

    /**
     * Starts a new game.
     */
    public void startGame() {
        currentState = GameState.PLAYING;
        gameInitializer.initGame();
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
        shopInitializer.initShop();
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
