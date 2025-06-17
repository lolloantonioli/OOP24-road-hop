package it.unibo.controller.Obstacles.api;

import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

/**
 * Interface for managing moving obstacles in the game.
 * This interface defines methods for creating, updating, and retrieving moving obstacles
 */
public interface MovingObstacleController {

    /**
     * Create a car obstacle at the specified position with the given speed.
     * @param y the Y coordinate (row) where the car will be created
     * @param count the number of cars to create
     * @param leftToRight the direction of movement (true = left to right, false = right to left)
     * @param speed the speed of the car
     * @return list of created car obstacles
     */
    List<MovingObstacles> createCarSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Create a train obstacle at the specified position with the given speed.
     * @param y the Y coordinate (row) where the train will be created
     * @param count the number of train cars to create
     * @param leftToRight the direction of movement (true = left to right, false = right to left)
     * @param speed the speed of the train
     * @return
     */
    List<MovingObstacles> createTrainSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Create a log obstacle at the specified position with the given speed.
     * @param y the Y coordinate (row) where the log will be created
     * @param count the number of logs to create
     * @param leftToRight the direction of movement (true = left to right, false = right to left)
     * @param speed the speed of the log
     * @return list of created log obstacles
     */
    List<MovingObstacles> createLogSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Updates the state of all moving obstacles.
     */
    void update();

    /**
     * Adds a new moving obstacle to the game.
     * @param type the type of the obstacle (e.g., CAR, TRAIN, LOG)
     * @return the created moving obstacle
     */
    List<MovingObstacles> getObstaclesByType(ObstacleType type);

    /**
     * Adds a new moving obstacle to the game.
     * @param obstacle the moving obstacle to add
     * @return true if the obstacle was added successfully, false otherwise
     */
    List<MovingObstacles> getAllObstacles();

    /**
     * Resets all obstacles in the game.
     * This method clears the list of obstacles and prepares for a new game or level.
     */
    void resetObstacles();

    /**
     * Generates obstacles based on the current difficulty level.
     * The difficulty level can affect the type, number, and speed of obstacles generated.
     * 
     * @param difficultyLevel the current difficulty level of the game
     */
    void generateObstacles(int difficultyLevel);

    /**
     * Increases the speed of all obstacles by a specified amount.
     * This method is typically used to make the game more challenging as the player progresses.
     * 
     * @param i the amount by which to increase the speed of all obstacles
     */
    void increaseAllObstaclesSpeed(int amount);
    
}
