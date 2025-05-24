package it.unibo.model.Map.impl;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.api.GameMap;

public class GameMapImpl implements GameMap {

    private final List<Chunk> chunks;
    private final ChunkFactory chunkFactory;
    private int currentPosition;
    private int scrollSpeed;

    public static final int CHUNKS_NUMBER = 7;
    private static final int BUFFER_CHUNKS = 5;
    private static final int MAX_SPEED = 10;

    public GameMapImpl(final int speed) {
        checkArgument(speed >= 0 && speed <= MAX_SPEED, "Speed must be between 0 and " + MAX_SPEED);
        this.chunks = new ArrayList<>();
        this.chunkFactory = new ChunkFactoryImpl();
        this.currentPosition = 0;
        this.scrollSpeed = speed;

        this.initializeMap();
    }

    private void initializeMap() {
        IntStream.range(0, CHUNKS_NUMBER)
            .forEach(i -> chunks.add(chunkFactory.createGrassChunk(i)));
    }

    @Override
    public void update() {
        currentPosition += scrollSpeed;
        this.cleanupChunks();
        this.ensureBufferChunks();
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
            .filter(chunk -> chunk.getPosition() >= currentPosition && chunk.getPosition() < currentPosition + CHUNKS_NUMBER)
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

}
