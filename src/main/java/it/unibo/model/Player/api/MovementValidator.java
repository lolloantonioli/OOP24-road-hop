package it.unibo.model.Player.api;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameMap;

/**
 * Interface for validating player movements in the game.
 * It provides methods to check if a player can move to a specified position
 * and if the player is out of bounds of the visible area.
 */
public interface MovementValidator {

    /**
     * Checks if the player can move to the specified cell.
     * 
     * @param map the game map
     * @param newPosition the cell the player tries to move to
     * @return true if the move is valid, false otherwise
     */
    boolean canMoveTo(GameMap map, Cell newPosition);

    /**
     * Checks if a cell is out of bounds of the visible area.
     * 
     * @param position the cell to check
     * @param map the game map
     * @return true if out of bounds, false otherwise
     */
    boolean isOutOfBounds(Cell position, GameMap map);

}
