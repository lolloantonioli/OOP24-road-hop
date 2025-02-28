package it.unibo.model.Map.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Map;

public class MapImpl implements Map {

    private final List<Chunk> chunks;
    private final int viewportHeight;
    private final int viewportWidth;
    private int currentPosition;

    public MapImpl(final int width, final int height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
        this.currentPosition = 0;
        this.chunks = new ArrayList<>();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void generateNewChunk() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateNewChunk'");
    }

    @Override
    public List<Chunk> getVisibleChunks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVisibleChunks'");
    }

    @Override
    public List<Chunk> getAllChunks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllChunks'");
    }

}
