package it.unibo.model.obstacles.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.controller.player.api.PlatformMovementObserver;
import it.unibo.model.map.api.Obstacle;
import it.unibo.model.map.impl.GameObjectImpl;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.obstacles.util.GameConstant;
import it.unibo.model.obstacles.util.SpeedConfig;

/**
 * Represent a moving obstacle in the game.
 * It handles the movement, visibility, and interaction with platforms.
 * It also manages observers that need to be notified when the obstacle moves.
 */
public final class MovingObstacles extends GameObjectImpl implements Obstacle {

    /**
     * Width of the car in cells.
     */
    public static final int CAR_WIDTH_CELLS = 1;

    /**
     * Width of the train in cells.
     */
    public static final int TRAIN_WIDTH_CELLS = 4;

    /**
     * Width of the log in cells.
     */
    public static final int LOG_WIDTH_CELLS = 3;

    /**
     * Number of cells in a chunk.
     */
    public static final int CELLS = GameConstant.CELLS_PER_CHUNK;

    private static final int BASE_MOVEMENT_THRESHOLD = 50; 
    private final ObstacleType type;
    private final List<PlatformMovementObserver> observers = new ArrayList<>();
    private boolean visible;
    private int updateCounter;

    /**
     * Constructs a new MovingObstacles instance.
     * 
     * @param cellX The x-coordinate in cells.
     * @param chunkY The y-coordinate in chunks.
     * @param type The type of the obstacle (e.g., CAR, TRAIN, LOG).
     * @param speed The speed of the obstacle.
     */
    public MovingObstacles(final int cellX, final int chunkY, final ObstacleType type, final int speed) {
        super(cellX, chunkY, getWidthForType(type));
        this.type = type;
        super.setSpeed(speed);
        super.setMovable(true);
        this.visible = true;
        this.updateCounter = 0;
    }

    /**
     * Returns the width of the obstacle in cells based on its type.
     * 
     * @param type The type of the obstacle.
     * @return The width in cells.
     */
    private static int getWidthForType(final ObstacleType type) {
        return switch (type.toString()) {
            case "TRAIN" -> TRAIN_WIDTH_CELLS;
            case "LOG" -> LOG_WIDTH_CELLS;
            default -> CAR_WIDTH_CELLS;
        };
    }

    @Override
    public ObstacleType getType() {
        return type;
    }

    /**
     * Updates the position of the obstacle based on its speed.
     */
    public void update() {
        if (!isMovable()) {
            return;
        }
        updateCounter++;
        final int movementThreshold = Math.max(1,  BASE_MOVEMENT_THRESHOLD - Math.abs(getSpeed())); 
        if (updateCounter >= movementThreshold) {
            updateCounter = 0;
            int deltaX = 0;
            if (getSpeed() > 0) {
                setX(getX() + 1);
                if (getX() >= CELLS) {
                    this.visible = false;
                }
                deltaX = 1;
            } else if (getSpeed() < 0) {
                setX(getX() - 1);
                if (getX() + getWidthInCells() - 1 < 0) {
                    this.visible = false;
                }
                deltaX = -1;
            }
            notifyObservers(deltaX);
        }
    }

    /**
     * Increases the speed of the obstacle by a specified amount.
     * 
     * @param amount The amount to increase the speed by.
     */
    public void increaseSpeed(final int amount) {
        final int maxSpeed;
        switch (type.toString()) {
            case "CAR" -> maxSpeed = SpeedConfig.CAP_CAR_SPEED;
            case "TRAIN" -> maxSpeed = SpeedConfig.CAP_TRAIN_SPEED;
            case "LOG" -> maxSpeed = SpeedConfig.CAP_LOG_SPEED;
            default -> throw new IllegalArgumentException("Unknown obstacle: " + type);
        }
        if (getSpeed() > 0) {
            setSpeed(Math.min(getSpeed() + amount, maxSpeed));
        } else if (getSpeed() < 0) {
            setSpeed(Math.max(getSpeed() - amount, -maxSpeed));
        }
    }

    @Override
    public boolean isPlatform() {
        return type == ObstacleType.LOG;
    }

    @Override
    public void setPlatform(final boolean platform) {
        if (platform && type != ObstacleType.LOG) {
            throw new UnsupportedOperationException("Only logs can be set as platforms.");
        }

    }

     /**
     * Checks if the obstacle is visible.
     * 
     * @return True if the obstacle is visible, false otherwise.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the obstacle.
     * 
     * @param visible True to make the obstacle visible, false to hide it.
     */
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    /**
     * Add an observer to the list of observers.
     * Observers will be notified when the platform moves.
     * @param obs
     */
    public void addObserver(final PlatformMovementObserver obs) {
        if (isPlatform()) {
            observers.add(obs);
        }
    }

    /**
     * Remove an observer from the list of observers.
     * @param obs
     */
    public void removeObserver(final PlatformMovementObserver obs) {
        observers.remove(obs);
    }

    /**
     * Notifies all observers about the movement of the platform.
     * This method is called when the obstacle moves.
     * 
     * @param deltaX The change in x-coordinate (movement).
     */
    private void notifyObservers(final int deltaX) {
        observers.forEach(o -> o.moveWithPlatform(deltaX));
    }

}
