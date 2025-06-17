package it.unibo.model.Obstacles.Util;

import java.util.Random;

/**
 * SpeedConfig class manages the speed configurations for different types of moving obstacles in the game.
 */
public class SpeedConfig {

    private static final int DEFAULT_MIN_CAR_SPEED = 15;
    private static final int DEFAULT_MAX_CAR_SPEED = 20;
    private static final int DEFAULT_MIN_TRAIN_SPEED = 25;
    private static final int DEFAULT_MAX_TRAIN_SPEED = 25;
    private static final int DEFAULT_MIN_LOG_SPEED = 10;
    private static final int DEFAULT_MAX_LOG_SPEED = 15;

    public static final int CAP_CAR_SPEED = 40;
    public static final int CAP_TRAIN_SPEED = 45;
    public static final int CAP_LOG_SPEED = 35;

    public static int minCarSpeed = DEFAULT_MIN_CAR_SPEED;
    public static int maxCarSpeed = DEFAULT_MAX_CAR_SPEED;
    public static int minTrainSpeed = DEFAULT_MIN_TRAIN_SPEED;
    public static int maxTrainSpeed = DEFAULT_MAX_TRAIN_SPEED;
    public static int minLogSpeed = DEFAULT_MIN_LOG_SPEED;
    public static int maxLogSpeed = DEFAULT_MAX_LOG_SPEED;

    public static void resetDefaultSpeeds() {
        minCarSpeed = DEFAULT_MIN_CAR_SPEED;
        maxCarSpeed = DEFAULT_MAX_CAR_SPEED;
        minTrainSpeed = DEFAULT_MIN_TRAIN_SPEED;
        maxTrainSpeed = DEFAULT_MAX_TRAIN_SPEED;
        minLogSpeed = DEFAULT_MIN_LOG_SPEED;
        maxLogSpeed = DEFAULT_MAX_LOG_SPEED;
    }

    public static int randomCarSpeed(Random rnd) {
        return minCarSpeed + rnd.nextInt(maxCarSpeed - minCarSpeed + 1);
    }
    public static int randomTrainSpeed(Random rnd) {
        return minTrainSpeed + rnd.nextInt(maxTrainSpeed - minTrainSpeed + 1);
    }
    public static int randomLogSpeed(Random rnd) {
        return minLogSpeed + rnd.nextInt(maxLogSpeed - minLogSpeed + 1);
    }
    
    public static void increaseAllSpeeds(int delta) {
        minCarSpeed = Math.min(minCarSpeed + delta, CAP_CAR_SPEED);
        maxCarSpeed = Math.min(maxCarSpeed + delta, CAP_CAR_SPEED);
        minTrainSpeed = Math.min(minTrainSpeed + delta, CAP_TRAIN_SPEED);
        maxTrainSpeed = Math.min(maxTrainSpeed + delta, CAP_TRAIN_SPEED);
        minLogSpeed = Math.min(minLogSpeed + delta, CAP_LOG_SPEED);
        maxLogSpeed = Math.min(maxLogSpeed + delta, CAP_LOG_SPEED);
    }
}
