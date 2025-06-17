package it.unibo.model.Obstacles.Util;

import java.util.Random;

/**
 * SpeedConfig class manages the speed configurations for different types of moving obstacles in the game.
 */
public class SpeedConfig {

    /**
     * Maximum speed limits for car.
     */
    public static final int CAP_CAR_SPEED = 40;

    /**
     * Maximum speed limits for train.
     */
    public static final int CAP_TRAIN_SPEED = 45;

    /**
     * Maximum speed limits for log.
     */
    public static final int CAP_LOG_SPEED = 35;

    /**
     * Speed increase value for obstacles.
     */
    public static final int INCREASE_SPEED = 5;

    /**
     * Default speed limits for different types of obstacles.
     */
    private static final int DEFAULT_MIN_CAR_SPEED = 15;
    private static final int DEFAULT_MAX_CAR_SPEED = 20;
    private static final int DEFAULT_MIN_TRAIN_SPEED = 25;
    private static final int DEFAULT_MAX_TRAIN_SPEED = 25;
    private static final int DEFAULT_MIN_LOG_SPEED = 10;
    private static final int DEFAULT_MAX_LOG_SPEED = 15;

    private static int minCarSpeed = DEFAULT_MIN_CAR_SPEED;
    private static int maxCarSpeed = DEFAULT_MAX_CAR_SPEED;
    private static int minTrainSpeed = DEFAULT_MIN_TRAIN_SPEED;
    private static int maxTrainSpeed = DEFAULT_MAX_TRAIN_SPEED;
    private static int minLogSpeed = DEFAULT_MIN_LOG_SPEED;
    private static int maxLogSpeed = DEFAULT_MAX_LOG_SPEED;

    private SpeedConfig() {
        // Private constructor to prevent instantiation
    }

    /**
     * Resets the speed limits to their default values.
     */
    public static void resetDefaultSpeeds() {
        minCarSpeed = DEFAULT_MIN_CAR_SPEED;
        maxCarSpeed = DEFAULT_MAX_CAR_SPEED;
        minTrainSpeed = DEFAULT_MIN_TRAIN_SPEED;
        maxTrainSpeed = DEFAULT_MAX_TRAIN_SPEED;
        minLogSpeed = DEFAULT_MIN_LOG_SPEED;
        maxLogSpeed = DEFAULT_MAX_LOG_SPEED;
    }

    /**
     * Generates a random speed for a car within the defined limits.    
     * @param rnd the Random instance to use for generating the speed
     * @return a random speed for a car
     */
    public static int randomCarSpeed(final Random rnd) {
        return minCarSpeed + rnd.nextInt(maxCarSpeed - minCarSpeed + 1);
    }

    /**
     * Generates a random speed for a train within the defined limits.
     * @param rnd the Random instance to use for generating the speed
     * @return a random speed for a train
     */
    public static int randomTrainSpeed(final Random rnd) {
        return minTrainSpeed + rnd.nextInt(maxTrainSpeed - minTrainSpeed + 1);
    }

    /**
     * Generates a random speed for a log within the defined limits.
     * @param rnd the Random instance to use for generating the speed
     * @return a random speed for a log
     */
    public static int randomLogSpeed(final Random rnd) {
        return minLogSpeed + rnd.nextInt(maxLogSpeed - minLogSpeed + 1);
    }

    /**
     * Increases all speed limits by a specified delta, ensuring they do not exceed their maximum caps.
     * @param delta the amount to increase the speeds by
     */
    public static void increaseAllSpeeds(final int delta) {
        minCarSpeed = Math.min(minCarSpeed + delta, CAP_CAR_SPEED);
        maxCarSpeed = Math.min(maxCarSpeed + delta, CAP_CAR_SPEED);
        minTrainSpeed = Math.min(minTrainSpeed + delta, CAP_TRAIN_SPEED);
        maxTrainSpeed = Math.min(maxTrainSpeed + delta, CAP_TRAIN_SPEED);
        minLogSpeed = Math.min(minLogSpeed + delta, CAP_LOG_SPEED);
        maxLogSpeed = Math.min(maxLogSpeed + delta, CAP_LOG_SPEED);
    }
}
