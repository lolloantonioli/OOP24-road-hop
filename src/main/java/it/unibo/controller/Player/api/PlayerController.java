package it.unibo.controller.Player.api;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Player.api.Player;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Shop.api.Skin;

public interface PlayerController {

    /**
     * Attempts to move the player in the specified direction.
     * 
     * @param direction the direction in which to move the player
     * @return true if the movement was successful, false otherwise
     */
    boolean movePlayer(Direction direction);

    /**
     * Processes all collisions for the current player position.
     * This includes handling collectibles, fatal collisions, and platform interactions.
     */
    void processCollisions();

    /**
     * Gets the current player instance.
     * 
     * @return the current player
     */
    Player getPlayer();

    /**
     * Gets the current position of the player as a Cell.
     * 
     * @return the player's current cell position
     */
    Cell getPlayerPosition();

    /**
     * Resets the player to the initial state and position.
     */
    void resetPlayer();

    /**
     * Updates the player's skin.
     * 
     * @param skin the new skin to apply to the player
     */
    void updatePlayerSkin(Skin skin);

    /**
     * Checks if the player is currently alive.
     * 
     * @return true if the player is alive, false otherwise
     */
    boolean isPlayerAlive();

    /**
     * Gets the current score of the player.
     * 
     * @return the player's current score
     */
    int getPlayerScore();

    /**
     * Gets the number of coins collected by the player.
     * 
     * @return the number of collected coins
     */
    int getCollectedCoins();

    /**
     * Updates the player state each game frame.
     * This method should be called from the main game loop to handle
     * continuous collision detection and state updates.
     */
    void update();

    /**
     * Checks if the player is currently on a platform (like a log on water).
     * 
     * @return true if the player is on a platform, false otherwise
     */
    boolean isPlayerOnPlatform();

    /**
     * Checks if the players has the second life.
     * 
     * @return true if the player has the second life, false otherwise.
     */
    boolean hasPlayerSecondLife();

}