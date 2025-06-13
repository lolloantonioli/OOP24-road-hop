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

    void showPausePanel(Runnable onContinue, Runnable onMenu);

    void hidePausePanel();

    /**
     * Getter for the ShopModel.
     * @return the ShopModel instance
     */
    ShopModel getShopModel();

    GameMap getGameMap();

    Optional<GameControllerImpl> getGameController();

    Optional<GameEngine> getGameEngine();

}
