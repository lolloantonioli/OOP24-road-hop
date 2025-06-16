package it.unibo.model.Map.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

/**
 * Implementation of the {@code Obstacle} interface.
 */
public final class ObstacleImpl extends GameObjectImpl implements Obstacle {

    private final ObstacleType type;

    /**
     * Creates a new {@code ObstacleImpl} instance.
     * @param x the x-coordinate of the obstacle
     * @param y the y-coordinate of the obstacle
     * @param type the type of the bstacle
     * @param movable whether the obstacle is movable
     */
    public ObstacleImpl(final int x, final int y, final ObstacleType type, final boolean movable) {
        super(x, y);
        super.setMovable(movable);
        this.type = type;
    }

    @Override
    public ObstacleType getType() {
        return this.type;
    }

}
