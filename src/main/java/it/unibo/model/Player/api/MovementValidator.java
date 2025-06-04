package it.unibo.model.Player.api;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;

public interface MovementValidator {

    /**
     * Checks if the player can move to the specified coordinates.
     * 
     * @param player the player
     * @param map the game map
     * @param newX the target X coordinate
     * @param newY the target Y coordinate
     * @return true if the move is valid, false otherwise
     */
    boolean canMoveTo(GameMap map, Cell newPosition);

    /**
     * Checks if the player is out of bounds of the visible area.
     * 
     * @param player the player
     * @param map the game map
     * @return true if out of bounds, false otherwise
     */
    boolean isOutOfBounds(Cell position, GameMap map);
    
}
