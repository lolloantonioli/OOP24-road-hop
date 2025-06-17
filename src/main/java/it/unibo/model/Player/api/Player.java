package it.unibo.model.player.api;

import it.unibo.controller.player.api.DeathObserver;
import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.GameMap;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.player.util.Direction;
import it.unibo.model.shop.api.Skin;

/**
 * Interface representing a player in the game.
 * It provides methods for movement, scoring, and player state management.
 */
public interface Player extends GameObject {

    /**
     * Moves the player in the the direction if is possible.
     * 
     * @param direction the direction the player tries to move in
     * @param map the game map
     * @param movementValidator a movement validator to check if the move is possible
     * @return true if the player was able to move false otherwise 
     */
    boolean tryMove(Direction direction, GameMap map, MovementValidator movementValidator);

    /**
     * Forces the player to move in the given direction.
     * 
     * @param newPos the cell the player moves in
     */
    void move(Cell newPos);

    /**
     * Gets the score of the player.
     * 
     * @return the player's score
     */
    int getScore();

    /**
     * Gets the cell the player is occuping.
     * 
     * @return an Optional containing the cell player is occuping if is valid, empty Optional otherwise
     */
    Cell getCurrentCell();


    /**
     * Checks if the player is alive.
     * 
     * @return true if the players is alive, false otherwise
     */
    boolean isAlive();

    /**
     * Handels the death of the player.
     */
    void die();

    /**
     * Resets the player to his initial state.
     */
    void reset();

    /**
     * Increases the number of coin collected.
    */
    void collectACoin();

    /**
     * Gets the number of coins collected by the player.
     * 
     * @return the number of collected coins
     */
    int getCollectedCoins();

    /**
     * Returns the current skin.
     * 
     * @return the player's current skin
     */
    Skin getCurrentSkin();

    /**
     * Sets the player's skin.
     * 
     * @param skin the new skin to set
     */
    void setSkin(Skin skin);

    /**
     * Check if the palyer has a second life.
     * 
     * @return true if the player has a second life, false otherwise
     */
    boolean hasSecondLife();

    /**
     * Gives to the player a second life.
     */
    void grantSecondLife();

    /**
     * Check if the player is invincible.
     * 
     * @return true if the player is invincible, false otherwise
     */
    boolean isInvincible();

    /**
     * Set if the player's position is out of bounds.
     * 
     * @param isOutOfBounds wheder the player is out of bound
     */
    void setOutOfBounds(boolean isOutOfBounds);

    /**
     * Check if the player is out of bounds.
     * 
     * @return true if the player is out of bounds, false otherwise
     */
    boolean isOutOfBounds();

    /**
     * Adds a Deathbserver to the player.
     * @param observer the DeathObserver to add
     */
    void addObserver(DeathObserver observer);

    /**
     * Removes a DeathObserver from the player.
     * @param observer the DeathObserver to remove
     */
    void removeObserver(DeathObserver observer);

    /**
     * Gets the initial X position of the player.
     * @return the initial X position
     */
    int getInitialX();

    /**
     * Gets the initial Y position of the player.
     * @return the initial Y position
     */
    int getInitialY();

}
