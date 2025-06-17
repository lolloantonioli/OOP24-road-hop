package it.unibo.model.Obstacles.api;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

/**
 * Factory interface for creating moving obstacles.
 */
public interface MovingObstacleFactory {

    /**
     * Creates a car obstacle moving at a specified speed.
     *
     * @param x Starting X-coordinate
     * @param y Y-coordinate
     * @param speed Movement speed (positive for right, negative for left)
     * @return A new car obstacle
     */
    MovingObstacles createCar(int x, int y, int speed);

    /**
     * Creates a train obstacle moving at a specified speed.
     *
     * @param x Starting X-coordinate
     * @param y Y-coordinate
     * @param speed Movement speed (positive for right, negative for left)
     * @return A new train obstacle
     */
    MovingObstacles createTrain(int x, int y, int speed);

    /**
     * Creates a log obstacle moving at a specified speed.
     *
     * @param x Starting X-coordinate
     * @param y Y-coordinate
     * @param speed Movement speed (positive for right, negative for left)
     * @return A new log obstacle
     */
    MovingObstacles createLog(int x, int y, int speed);

    /**
     * Creates a moving obstacle based on its type and position.
     *
     * @param type Type of obstacle
     * @param x Starting X-coordinate
     * @param y Y-coordinate
     * @param speed Movement speed (positive for right, negative for left)
     * @return A new moving obstacle
     */
    MovingObstacles createObstacleByType(ObstacleType type, int x, int y, int speed);

    /**
     * Gets a random speed for the specified obstacle type.
     *
     * @param type Type of obstacle
     * @return Random speed within the defined range for the obstacle type
     */
    int getRandomSpeed(ObstacleType type);

    /**
     * Increases the speed limits for all obstacles by a specified amount.
     *
     * @param amount Amount to increase the speed limits
     */
    void increaseSpeedLimits(int amount);
    
    /**
     * Gets the width of the obstacle in cells.
     *
     * @param type Type of obstacle
     * @return Width in cells
     */
    int getObstacleWidth(ObstacleType type);
}
