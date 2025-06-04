package it.unibo.model.Map.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableSet;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;

public class CellImpl implements Cell {

    private Set<GameObject> content;
    private final int x;
    private final int y;

    private static final String MSG = "Coordinates must be non-negative";

    public CellImpl(final int x, final int y) {
        checkArgument(x >= 0 && y >= 0, MSG);
        this.x = x;
        this.y = y;
        this.content = new HashSet<>();
    }

    @Override
    public boolean addObject(final GameObject obj) {
        return content.add(obj);
    }

    @Override
    public boolean removeObject(final GameObject obj) {
        if (content.contains(obj)) {
            content.remove(obj);
            return true;
        }
        return false;
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
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

}
