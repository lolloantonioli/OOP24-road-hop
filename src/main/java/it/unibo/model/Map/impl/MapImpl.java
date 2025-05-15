package it.unibo.model.Map.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Map;

public class MapImpl implements Map {

    private final List<Chunk> chunks;
    private final int viewportHeight;
    private final int viewportWidth;
    private int currentPosition;
    private int scrollSpeed;

    public MapImpl(final int width, final int height, final int speed) {
        this.chunks = new ArrayList<>();
        this.viewportWidth = width;
        this.viewportHeight = height;
        this.currentPosition = 0;
        this.scrollSpeed = speed;
    }

    @Override
    public void update() {
        this.currentPosition += this.scrollSpeed;
        this.cleanChunks();
    }

    private void cleanChunks() {
        Iterator<Chunk> iterator = chunks.iterator();
        while (iterator.hasNext()) {
            Chunk chunk = iterator.next();
            if (chunk.getPosition() + chunk.getHeight() < currentPosition - viewportHeight) {
                iterator.remove();
            }
        }
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
        return this.chunks;
    }

}
