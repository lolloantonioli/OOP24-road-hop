package it.unibo.model.Player.api;

import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Player.util.Direction;
import it.unibo.model.Collision.api.CollisionProMax;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Shop.api.Skin;

import java.util.Optional;

// può servire un getter della massima posizione raggiunta?

public interface Player extends GameObject {

    /**
     * Moves the player in the the direction if is possible
     * 
     * @param direcrion the direction the player tries to move in
     * @param map the game map
     * @param collisionHandler a collisioHandler to check if the move is possible
     * @return true if the player was able to move false otherwise 
     */
    boolean tryMove(Direction direction, GameMap map, CollisionProMax collisionHandler);

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

    

}
