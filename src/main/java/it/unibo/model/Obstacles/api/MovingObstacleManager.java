package it.unibo.model.Obstacles.api;

import java.util.List;

import it.unibo.model.Obstacles.impl.MovingObstacles;

/**
 * Interface for managing moving obstacles in the game.
 */
public interface MovingObstacleManager {
    
    /**
     * Adds a moving obstacle to be managed.
     * 
     * @param obstacle The obstacle to add
     */
    void addObstacle(MovingObstacles obstacle);
    
    /**
     * Adds multiple obstacles at once.
     * 
     * @param obstacles Array of obstacles to add
     */
    void addObstacles(List<MovingObstacles> obstacles);
    
    /**
     * Removes an obstacle from management.
     * 
     * @param obstacle The obstacle to remove
     
    void removeObstacle(MovingObstacles obstacle);*/
    
    /**
     * Updates the state of all managed obstacles.
     */
    void updateAll();
    
    /**
     * Gets all currently active obstacles.
     * 
     * @return List of active obstacles
     */
    List<MovingObstacles> getActiveObstacles();
    
    /**
     * Gets obstacles of a specific type.
     * 
     * @param type Type of obstacles to get
     * @return List of obstacles of the specified type
     */
    List<MovingObstacles> getObstaclesByType(String type);

    /**
     * Gets obstacles in a specific chunk.
     * 
     * @param chunkY Y coordinate of the chunk
     * @return List of obstacles in the specified chunk
     */
    List<MovingObstacles> getObstaclesInChunk(int chunkY);
    
    /**
     * Checks if a point collides with any managed obstacle.
     * 
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return True if there is a collision
     
    boolean checkCollision(int cellX, int chunkY);*/
    
    /**
     * Increases the speed of all obstacles by a factor.
     * Used when difficulty increases.
     * 
     * @param factor Speed increase factor
     */
    void increaseSpeed(int factor);
    
    /**
     * Removes obstacles that are outside the visible area.
    
     */
    void cleanupOffscreenObstacles();
    
    /**
     * Gets the total number of managed obstacles.
     * 
     * @return Count of obstacles
     
    int getObstacleCount();*/

    /**
    * Resets all obstacles to their initial position and state.
    */
    void resetAll();

    /**
     * Checks collision with a specific area (useful for multi-cell obstacles).
     * 
     * @param startCellX Starting X cell position
     * @param endCellX Ending X cell position
     * @param chunkY Y coordinate in chunks
     * @return True if there is a collision in the area
     
    boolean checkCollisionInArea(int startCellX, int endCellX, int chunkY);*/

    /**
     * Checks if a position is safe for placing a new obstacle.
     * 
     * @param cellX X position in cells
     * @param chunkY Y position in chunks
     * @param widthInCells Width of the obstacle in cells
     * @return True if the position is safe
     */
    boolean isSafePosition(int cellX, int chunkY, int widthInCells);
}
