package it.unibo.model.Player.api;

import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Shop.api.Skin;

// può servire un getter della massima posizione raggiunta?

public interface Player extends GameObject {

    /**
     * Moves the player in the the direction if is possible
     * 
     * @param direcrion the direction the player tries to move in
     * @param map the game map
     * @param movementValidator a movement validator to check if the move is possible
     * @return true if the player was able to move false otherwise 
     */
    boolean tryMove(Direction direction, GameMap map, MovementValidator movementValidator);

    /**
     * Forces the player to move in the given direction
     * 
     * @param newPos the cell the player moves in
     */
    void move(Cell newPos);

    /**
     * Gets the score of the player
     * 
     * @return the player's score
     */
    int getScore();

    /**
     * Gets the cell the player is occuping
     * 
     * @return an Optional containing the cell player is occuping if is valid, empty Optional otherwise
     */
    Cell getCurrentCell();


    /**
     * Checks if the player is alive
     * 
     * @return true if the players is alive, false otherwise
     */
    boolean isAlive();

    /**
     * Handels the death of the player
     */
    void die();

    /**
     * Resets the player to his initial state
     */
    void reset();

    /**
     * Increases the number of coin collected
    */
    void collectACoin();

    /**
     * Gets the number of coins collected by the player
     * 
     * @return the number of collected coins
     */
    int getCollectedCoins();

    /**
     * Returns the current skin
     * 
     * @return the player's current skin
     */
    Skin getCurrentSkin();

    /**
     * Sets the player's skin
     * 
     * @param skin the new skin to set
     */
    void setSkin(Skin skin);

    /**
     * Check if the palyer has a second life
     * 
     * @return true if the player has a second life, false otherwise
     */
    public boolean hasSecondLife();

    /**
     * Gives to the player a second life
     */
    public void grantSecondLife();

    /**
     * Check if the player is invincible
     * 
     * @return true if the player is invincible, false otherwise
     */
    public boolean isInvincible();

    /**
     * Set if the player's position is out of bounds
     * 
     * @param isOutOfBounds wheder the player is out of bound
     */
    public void setOutOfBounds(boolean isOutOfBounds);

    /**
     * Check if the player is out of bounds
     * 
     * @return true if the player is out of bounds, false otherwise
     */
    public boolean isOutOfBounds();

}
