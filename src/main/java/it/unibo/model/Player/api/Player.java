package it.unibo.model.Player.api;

import it.unibo.model.Player.util.Direction;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Shop.api.Skin;

import java.util.Optional;

// può servire un getter della massima posizione raggiunta?

public interface Player extends GameObject {

    /**
     * Moves the player in the specified direction if it's possible
     * 
     * @param direction the direction to move in
     * @return true if the player was able to move false otherwise 
     */
    boolean move(Direction direction);

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
    Optional<Cell> getCurrentCell();


    /**
     * Checks if the player is alive
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
     * Gets the number of coins collected by the player
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

    int getPlayerHeight();

    int getPlayerWidth();

}
