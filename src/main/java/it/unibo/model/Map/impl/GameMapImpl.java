package it.unibo.model.Map.impl;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.api.GameMap;

/**
 * Implementation of the {@code GameMap} interface.
 */
public final class GameMapImpl implements GameMap {

    private final List<Chunk> chunks;
    private final ChunkFactory chunkFactory;
    private int currentPosition;
    private int scrollSpeed;

    public final static int CHUNKS_NUMBER = 7;
    
    private final static int BUFFER_CHUNKS = 5;
    private final static int MAX_SPEED = 10;
    private final static int CELLS_INCREASE_SPEED = 70;

    /**
     * Constructs a new {@code GameMapImpl} with the specified scroll speed.
     */
    public GameMapImpl() {
        this.chunks = new ArrayList<>();
        this.chunkFactory = new ChunkFactoryImpl();
        this.currentPosition = 0;
        this.scrollSpeed = 1;

        this.initializeMap();
    }

    private void initializeMap() {
        chunks.add(chunkFactory.createFirstChunk(0));
        IntStream.range(1, CHUNKS_NUMBER + 1)
            .forEach(i -> chunks.add(chunkFactory.createGrassChunk(i)));
    }

    @Override
    public void update() {
        currentPosition += 1;
        this.cleanupChunks();
        this.ensureBufferChunks();
        if (currentPosition > 0 && currentPosition % CELLS_INCREASE_SPEED == 0) {
            this.increaseScrollSpeed();
        }
    }

    private void cleanupChunks() {
        chunks.removeIf(chunk -> chunk.getPosition() < currentPosition - CHUNKS_NUMBER);
    }

    private void ensureBufferChunks() {
        int farthestPosition = getFarthestChunkPosition();
        final int targetPosition = currentPosition + BUFFER_CHUNKS + CHUNKS_NUMBER;

        while (farthestPosition < targetPosition) {
            generateNewChunk();
            farthestPosition = getFarthestChunkPosition();
        }
    }

    private int getFarthestChunkPosition() {
        return chunks.stream()
            .mapToInt(Chunk::getPosition)
            .max()
            .orElse(0);
    }

    @Override
    public void generateNewChunk() {
        final int nextPosition = getFarthestChunkPosition() + 1;
        final Chunk newChunk = chunkFactory.createRandomChunk(nextPosition);
        chunks.add(newChunk);
    }

    @Override
    public List<Chunk> getVisibleChunks() {
        return chunks.stream()
            .filter(chunk -> chunk.getPosition() >= currentPosition && chunk.getPosition() < currentPosition + CHUNKS_NUMBER + 1)
            .toList();
    }

    @Override
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public void increaseScrollSpeed() {
        scrollSpeed = Math.min(scrollSpeed + 1, MAX_SPEED);
    }

    @Override
    public List<Chunk> getAllChunks() {
        return ImmutableList.copyOf(chunks);
    }

    @Override
    public int getScrollSpeed() {
        return this.scrollSpeed;
    }

}
