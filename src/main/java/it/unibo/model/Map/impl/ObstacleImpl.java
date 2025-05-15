package it.unibo.model.Map.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

public class ObstacleImpl extends GameObjectImpl implements Obstacle {

    private final ObstacleType type;

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
