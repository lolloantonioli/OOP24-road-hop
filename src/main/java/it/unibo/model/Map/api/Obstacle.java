package it.unibo.model.Map.api;

import it.unibo.model.Map.util.ObstacleType;

/**
 * Represents an obstacle in the game.
 * Obstacles are objects that can block the player's movement or kill the player.
 */
public interface Obstacle extends GameObject{
    
    /**
     * Returns the type of the obstacle.
     * 
     * @return the type of the obstacle
     */
    ObstacleType getType();
    
}
