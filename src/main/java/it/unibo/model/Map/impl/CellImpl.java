package it.unibo.model.Map.impl;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableSet;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.util.Position;

/**
 * Implementation of the {@code Cell} interface.
 * Represents a cell in the game map that can contain multiple {@code GameObject}.
 */
public class CellImpl implements Cell {

    private final Set<GameObject> content;
    private final Position position;

    private static final String CONSTRUCTOR_MSG = "Coordinates must be non-negative";
    private static final String NULL_MSG = "GameObject cannot be null";

    /**
     * Constructs a new {@code CellImpl} with the specified coordinates.
     *
     * @param position the position of the cell.
     * @throws IllegalArgumentException if x or y is negative.
     */
    public CellImpl(final int x, final int y) {
        checkArgument(x >= 0 && y >= 0, CONSTRUCTOR_MSG);
        this.position = new Position(x, y);
        this.content = new HashSet<>();
    }

    @Override
    public boolean addObject(final GameObject obj) {
        checkArgument(obj != null, NULL_MSG);
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

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CellImpl cell = (CellImpl) obj;
        return getX() == cell.getX() && getY() == cell.getY();
    }

}
