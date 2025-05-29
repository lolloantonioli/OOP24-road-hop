package it.unibo.model.Map.impl;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameObject;

public class CellImpl implements Cell {

    private Optional<GameObject> content;
    private final int x;
    private final int y;

    private static final String MSG = "Coordinates must be non-negative";

    public CellImpl(final int x, final int y) {
        checkArgument(x >= 0 && y >= 0, MSG);
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

    @Override
    public boolean isOccupiedInChunk(Chunk chunk) {
        // controlla se la cella contiene un oggetto
        if (hasObject()) {
            return true;
        }
        
        // controlla se un oggetto multicella occupa la cella
        for (GameObject obj : chunk.getObjects()) {
            if (obj.occupiesCell(this.getX())) {
                return true;
            }
        }
        
        return false;
    }

}
