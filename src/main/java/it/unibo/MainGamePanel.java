package it.unibo;

import com.google.common.math.Quantiles.Scale;

import it.unibo.controller.GameState;
import it.unibo.controller.GameStateManager;
import it.unibo.controller.Menu.api.MenuController;
import it.unibo.controller.Menu.impl.MenuControllerImpl;
import it.unibo.view.ScaleManager;
import it.unibo.view.Menu.api.MenuView;
import it.unibo.view.Menu.impl.MenuViewImpl;


public class MainGamePanel {

    private ScaleManager scaleManager;
    private MenuController menuController;
    private final GameStateManager gameStateManager;

    private MenuView menuView;

    public MainGamePanel(int width, int height) {

        
        // Initialize the game state manager
        gameStateManager = new GameStateManager();
        this.gameStateManager.setState(GameState.MENU);
    }

    /**
     * Initializes the menu components.
     */
    private void initMenu() {
        menuView = new MenuViewImpl(scaleManager);
        
    }
    private void handleMouseClick(int x, int y) {
        // Handle mouse click events based on the current game state
        switch (gameStateManager.getCurrentState()) {
            case MENU:
            menuController.handleClick(x, y);
                break;
            case PLAYING:
                // Handle game logic
                break;
            case SHOP:
                // Handle shop logic
                break;
            case GAME_OVER:
                // Handle game over logic
                break;
    }
}
}
