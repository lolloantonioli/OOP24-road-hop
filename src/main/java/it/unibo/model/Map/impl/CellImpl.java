package it.unibo.model.map.impl;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableSet;

import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.GameObject;
import it.unibo.model.map.util.Position;

/**
 * Implementation of the {@code Cell} interface.
 * Represents a cell in the game map that can contain multiple {@code GameObject}.
 */
public final class CellImpl implements Cell {

    private static final String CONSTRUCTOR_MSG = "Coordinates must be non-negative";
    private static final String NULL_MSG = "GameObject cannot be null";

    private final Set<GameObject> content;
    private final Position position;

    /**
     * Constructs a new {@code CellImpl} with the specified coordinates.
     *
     * @param x the x coordinate of the cell.
     * @param y the y coordinate of the cell.
     * @throws IllegalArgumentException if x or y is negative.
     */
    public CellImpl(final int x, final int y) {
        checkNotNull(x, CONSTRUCTOR_MSG);
        checkNotNull(y, CONSTRUCTOR_MSG);
        this.position = new Position(x, y);
        this.content = new HashSet<>();
    }

    @Override
    public boolean addObject(final GameObject obj) {
        checkNotNull(obj, NULL_MSG);
        // Allow multiple different objects, but prevent duplicates
        if (content.contains(obj)) {
            return false;
        }
        return content.add(obj);
    }

    @Override
    public boolean removeObject(final GameObject obj) {
        checkArgument(obj != null, NULL_MSG);
        return content.remove(obj);
    }

    @Override
    public boolean hasObject() {
        return !content.isEmpty();
    }

    @Override
    public Set<GameObject> getContent() {
        return ImmutableSet.copyOf(content);
    }

    @Override
    public int getX() {
        return this.position.x();
    }

    @Override
    public int getY() {
        return this.position.y();
    }

}
