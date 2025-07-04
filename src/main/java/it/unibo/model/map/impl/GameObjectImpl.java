package it.unibo.model.map.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.map.api.GameObject;

/**
 * Base implementation for game objects placed on the map.
 * <p>
 * Subclasses may override these methods, but should ensure consistency between getters and setters,
 * and preserve the intended semantics of each property.
 */
public class GameObjectImpl implements GameObject {

    private static final String MSG = "Width in cells must be at least 1";
    private static final String CELL_MSG = "Cell X must be between 0 and " + ChunkImpl.CELLS_PER_ROW;

    private int x;
    private int y;
    private boolean movable;
    private int speed;
    private boolean platform;
    private int widthInCells = 1; // Default width in cells

    /**
     * Constructs a new {@code GameObjectImpl} with the specified coordinates.
     * This constructor initializes the object with a default width of 1 cell.
     *
     * @param x the x coordinate of the object.
     * @param y the y coordinate of the object.
     * @throws NullPointerException if x or y is null.
     */
    public GameObjectImpl(final int x, final int y) {
        this(checkNotNull(x), checkNotNull(y), 1);
    }

    /**
     * Constructs a new {@code GameObjectImpl} with the specified coordinates and width in cells.
     *
     * @param x the x coordinate of the object.
     * @param y the y coordinate of the object.
     * @param widthInCells the width of the object in cells.
     * @throws IllegalArgumentException if widthInCells is less than 1.
     * @throws NullPointerException if x or y is null.
     */
    public GameObjectImpl(final int x, final int y, final int widthInCells) {
        checkArgument(widthInCells >= 1, MSG);
        this.x = checkNotNull(x);
        this.y = checkNotNull(y);
        this.movable = false;
        this.speed = 0;
        this.platform = false;
        this.widthInCells = widthInCells;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure consistency with {@link #setX(int)}.
     */
    @Override
    public int getX() {
        return this.x;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure consistency with {@link #setY(int)}.
     */
    @Override
    public int getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure consistency with {@link #setSpeed(int)}.
     */
    @Override
    public int getSpeed() {
        return this.speed;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure the returned value reflects the object's movability.
     */
    @Override
    public boolean isMovable() {
        return this.movable;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the movability state accordingly.
     */
    @Override
    public void setMovable(final boolean movable) {
        this.movable = movable;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the speed state accordingly.
     */
    @Override
    public void setSpeed(final int speed) {
        this.speed = checkNotNull(speed);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure the returned value reflects the object's platform state.
     */
    @Override
    public boolean isPlatform() {
        return this.platform;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the platform state accordingly.
     */
    @Override
    public void setPlatform(final boolean platform) {
        this.platform = checkNotNull(platform);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the X coordinate accordingly.
     */
    @Override
    public void setX(final int x) {
        this.x = checkNotNull(x);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the Y coordinate accordingly.
     */
    @Override
    public void setY(final int y) {
        this.y = checkNotNull(y);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should update the width in cells accordingly.
     */
    @Override
    public void setWidthInCells(final int widthInCells) {
        checkArgument(widthInCells >= 1, CELL_MSG);
        this.widthInCells = widthInCells;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure the returned value reflects the object's width in cells.
     */
    @Override
    public int getWidthInCells() {
        return widthInCells;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Subclasses overriding this method should ensure the returned list accurately represents all occupied cells.
     */
    @Override
    public List<Integer> getXes() {
        final int width = getWidthInCells();
        final List<Integer> cells = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            cells.add(getX() + i);
        }
        return cells;
    }

}
