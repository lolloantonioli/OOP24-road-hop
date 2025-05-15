package it.unibo.model.Map.impl;

import java.util.Optional;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.GameObject;

public class CellImpl implements Cell {

    private Optional<GameObject> content;
    private final int x;
    private final int y;

    public CellImpl(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.content = Optional.empty();
    }

    @Override
    public boolean addObject(final GameObject obj) {
        if (!content.isPresent()) {
            content = Optional.of(obj);
            return true;
        }
        return false;
    }

    @Override
    public void removeObject() {
        content = Optional.empty();
    }

    @Override
    public boolean hasObject() {
        return content.isPresent();
    }

    @Override
    public Optional<GameObject> getContent() {
        return this.content;
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
