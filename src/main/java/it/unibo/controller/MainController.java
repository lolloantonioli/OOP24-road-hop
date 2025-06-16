package it.unibo.controller;

import java.util.Optional;

import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Shop.api.ShopModel;

/**
 * MainController interface that defines the methods for navigating between different game states
 * such as menu, game, instructions, and shop.
 * It also provides access to the ShopModel and MapController.
 */
public interface MainController {

    /**
     * Navigates to the main menu of the game.
     */
    void goToMenu();

    /**
     * Navigates to the game state and starts the game engine.
     */
    void goToGame();

    /**
     * Navigates to the instructions screen.
     */
    void goToInstructions();
    
    /**
     * Navigates to the shop where players can purchase skins
     */
    void goToShop();

    /**
     * Shows the pause panel with options to continue or return to the menu.
     * @param onContinue the action to perform when continuing the game
     * @param onMenu the action to perform when returning to the menu
     */
    void showPausePanel(Runnable onContinue, Runnable onMenu);

    /**
     * Hides the pause panel.
     */
    void hidePausePanel();
    
    /**
     * Shows the game over panel.
     */
    void showGameOverPanel();

    /**
     * Getter for the ShopModel.
     * @return the ShopModel instance
     */
    ShopModel getShopModel();

    /**
     * Getter for the GameMap.
     * @return the GameMap instance
     */
    GameMap getGameMap();

    /**
     * Getter for the GameController if is present.
     * @return an Optional containing the GameControllerImpl if it exists, otherwise an empty Optional
     */
    Optional<GameController> getGameController();

    /**
     * Getter for the GameEngine if is present.
     * @return an Optional containing the GameEngine if it exists, otherwise an empty Optional
     */
    Optional<GameEngine> getGameEngine();

}
