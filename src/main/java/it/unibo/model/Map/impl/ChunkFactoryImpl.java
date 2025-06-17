package it.unibo.model.map.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Random;

import it.unibo.model.map.api.Chunk;
import it.unibo.model.map.api.ChunkFactory;
import it.unibo.model.map.api.ObjectPlacer;
import it.unibo.model.map.util.ChunkType;

/**
 * Implementation of the {@code ChunkFactory} interface.
 * This factory is responsible for creating different types of chunks at specified positions.
 */
public final class ChunkFactoryImpl implements ChunkFactory {

    private static final String MSG = "Position must be non-negative";

    private final Random random;
    private final ObjectPlacer objectPlacer;

    /**
     * Constructs a new {@code ChunkFactoryImpl} with a random number generator and an {@code ObjectPlacer}.
     */
    public ChunkFactoryImpl() {
        this.random = new Random();
        this.objectPlacer = new ObjectPlacerImpl();
    }

    @Override
    public Chunk createRandomChunk(final int position) {
        checkArgument(position >= 0, MSG);
        final int type = random.nextInt(8);
        return switch (type) {
            case 0, 1 -> createRoadChunk(position);
            case 2, 3 -> createRailwayChunk(position);
            case 4 -> createRiverChunk(position);
            default -> createGrassChunk(position);
        };
    }

    private Chunk createChunk(final int position, final ChunkType type,
                              final boolean placeObstacles, final boolean placeCollectibles) {
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

    @Override
    public Chunk createFirstsChunk(final int position) {
        return createChunk(position, ChunkType.GRASS, false, false);
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
