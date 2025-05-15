package it.unibo.model.Map.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameMap;

public class GameMapImpl implements GameMap {

    private final List<Chunk> chunks;
    private int currentPosition;
    private int scrollSpeed;

    private static final int BUFFER_CHUNKS = 5;
    private static final int CHUNKS_NUMBER = 7;

    public GameMapImpl(final int speed) {
        this.chunks = new ArrayList<>();
        this.currentPosition = 0;
        this.scrollSpeed = speed;

        this.initializeMap();
    }

    private void initializeMap() {
        
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
    public int getCurrentPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentPosition'");
    }

    @Override
    public void increaseScrollSpeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'increaseScrollSpeed'");
    }

    @Override
    public boolean isPositionOutOfBounds(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isPositionOutOfBounds'");
    }

    @Override
    public List<Chunk> getAllChunks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllChunks'");
    }

    @Override
    public int getMapWidth() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMapWidth'");
    }

    @Override
    public int getMapHeight() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMapHeight'");
    }

}
