package it.unibo.model.Map.impl;

import static com.google.common.base.Preconditions.checkArgument;

import it.unibo.model.Map.api.GameObject;

public class GameObjectImpl implements GameObject {

    private int x;
    private int y;
    private boolean movable;
    private int speed;
    private boolean platform;

    public GameObjectImpl(final int x, final int y) {
        checkArgument(x >= 0 && y >= 0, "Coordinates must be non-negative");
        this.x = x;
        this.y = y;
        this.movable = false;
        this.speed = 0;
        this.platform = false;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public boolean isMovable() {
        return this.movable;
    }

    @Override
    public void setMovable(final boolean movable) {
        this.movable = movable;
    }

    @Override
    public void setSpeed(final int speed) {
        checkArgument(speed >= 0, "Speed must be non-negative");
        this.speed = speed;
    }

    @Override
    public boolean isPlatform() {
        return this.platform;
    }

    @Override
    public void setPlatform(final boolean platform) {
        this.platform = platform;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

}
