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
        if (position < 0) {
            throw new IllegalArgumentException("Position must be non-negative");
        }
        final int type = random.nextInt(4);
        return switch (type) {
            case 0 -> createRoadChunk(position);
            case 1 -> createRailwayChunk(position);
            case 2 -> createRiverChunk(position);
            default -> createGrassChunk(position);
        };
    }

    private Chunk createChunk(final int position, final ChunkType type, final boolean placeObstacles, final boolean placeCollectibles) {
        final Chunk chunk = new ChunkImpl(position, type);
        if (placeObstacles) {
            objectPlacer.placeObstacles(chunk);
        }
        if (placeCollectibles) {
            objectPlacer.placeCollectibles(chunk);
        }
        return chunk;
    }

    @Override
    public Chunk createGrassChunk(final int position) {
        return createChunk(position, ChunkType.GRASS, true, true);
    }

    private Chunk createRoadChunk(final int position) {
        return createChunk(position, ChunkType.ROAD, false, true);
    }

    private Chunk createRailwayChunk(final int position) {
        return createChunk(position, ChunkType.RAILWAY, false, true);
    }

    private Chunk createRiverChunk(final int position) {
        return createChunk(position, ChunkType.RIVER, false, false);
    }

}
