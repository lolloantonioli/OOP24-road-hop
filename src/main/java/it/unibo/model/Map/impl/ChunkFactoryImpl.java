package it.unibo.model.Map.impl;

import java.util.Random;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.api.ObjectPlacer;

public class ChunkFactoryImpl implements ChunkFactory {

    private final Random random;
    private final ObjectPlacer objectPlacer;

    public ChunkFactoryImpl() {
        this.random = new Random();
        this.objectPlacer = new ObjectPlacerImpl();
    }

    @Override
    public Chunk createRandomChunk(final int position) {
        final int type = random.nextInt(4);
        return switch (type) {
            case 0 -> createRoadChunk(position);
            case 1 -> createRailwayChunk(position);
            case 2 -> createRiverChunk(position);
            default -> createGrassChunk(position);
        };
    }

    @Override
    public Chunk createGrassChunk(final int position) {
        final Chunk chunk = new ChunkImpl(position, ChunkType.GRASS);
        objectPlacer.placeObstacles(chunk);
        objectPlacer.placeCollectibles(chunk);
        return chunk;
    }

    private Chunk createRoadChunk(final int position) {
        final Chunk chunk = new ChunkImpl(position, ChunkType.ROAD);
        objectPlacer.placeCollectibles(chunk);
        return chunk;
    }

    private Chunk createRailwayChunk(final int position) {
        final Chunk chunk = new ChunkImpl(position, ChunkType.RAILWAY);
        objectPlacer.placeCollectibles(chunk);
        return chunk;
    }

    private Chunk createRiverChunk(final int position) {
        return new ChunkImpl(position, ChunkType.RIVER);
    }

}
