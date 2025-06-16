package it.unibo.controller;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.controller.Player.api.PlayerController;
import it.unibo.model.Map.api.GameMap;

/**
 * Interface for the main game controller.
 * Defines the contract for updating and accessing game components.
 */
public interface GameController {

    /**
     * Updates the game map state (e.g., scrolling, chunk management).
     */
    void updateMap();

    /**
     * Updates the state of all moving obstacles.
     */
    void updateObstacles();

    /**
     * Updates the player state (e.g., position, status).
     */
    void updatePlayer();

    /**
     * Returns the current game map.
     * @return the game map
     */
    GameMap getGameMap();

    /**
     * Returns the controller for moving obstacles.
     * @return the moving obstacle controller
     */
    MovingObstacleController getObstacleController();

    /**
     * Returns the player controller.
     * @return the player controller
     */
    PlayerController getPlayerController();

    /**
     * Returns the map formatter/adapter for the view.
     * @return the map formatter
     */
    MapFormatter getMapFormatter();

    /**
     * Returns the width of the map (number of cells per row).
     * @return the map width
     */
    int getMapWidth();

    /**
     * Returns the height of the map (number of chunks).
     * @return the map height
     */
    int getMapHeight();
}
