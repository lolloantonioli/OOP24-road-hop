package it.unibo.model.Map.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.ObjectPlacer;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

/**
 * Implementation of the {@code ChunkFactory} interface.
 * This factory is responsible for creating different types of chunks at specified positions.
 */
public class ChunkFactoryImpl implements ChunkFactory {

    private final Random random;
    private final ObjectPlacer objectPlacer;

    private final static String MSG = "Position must be non-negative";

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

    @Override
    public Chunk createFirstChunk(final int position) {
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

    private static class ObjectPlacerImpl implements ObjectPlacer {

        private final List<List<Integer>> patterns;
        private final Random random;

        // Variabile di istanza per tenere traccia della posizione "safe" tra le righe
        private int lastSafeCell = -1;

        private ObjectPlacerImpl() {
            this.patterns = new ArrayList<>();
            this.random = new Random();
            patterns.add(List.of(0, 2, 4));
            patterns.add(List.of(1, 3, 5));
            patterns.add(List.of(0, 3, 6));
            patterns.add(List.of(1, 4, 7));
            patterns.add(List.of(2, 5, 8));
            patterns.add(List.of(0, 4, 8));
            patterns.add(List.of(0, 2));
            patterns.add(List.of(5, 7));
            patterns.add(List.of());
        }

        @Override
        public void placeObstacles(final Chunk chunk) {
            // Safe cell collegata tra le righe
            int safeCell;
            if (lastSafeCell == -1) {
                safeCell = random.nextInt(ChunkImpl.CELLS_PER_ROW);
            } else {
                int min = Math.max(0, lastSafeCell - 1);
                int max = Math.min(ChunkImpl.CELLS_PER_ROW - 1, lastSafeCell + 1);
                safeCell = min + random.nextInt(max - min + 1);
            }
            lastSafeCell = safeCell;
            final int patternIndex = random.nextInt(patterns.size());
            final List<Integer> selectedPattern = patterns.get(patternIndex);
            selectedPattern.forEach(pos -> {
                if (pos < ChunkImpl.CELLS_PER_ROW && pos != safeCell) {
                    final Obstacle tree = new ObstacleImpl(pos, chunk.getPosition(), ObstacleType.TREE, false);
                    chunk.addObjectAt(tree, pos);
                }
            });
        }

        @Override
        public void placeCollectibles(final Chunk chunk) {
            if (random.nextDouble() < 0.4) {
                final Set<Integer> occupiedPositions = new HashSet<>();
                chunk.getObjects().stream()
                    .filter(obj -> obj instanceof Obstacle)
                    .forEach(obj -> occupiedPositions.add(obj.getX()));
                final List<Integer> availablePositions = new ArrayList<>();
                IntStream.range(0, ChunkImpl.CELLS_PER_ROW)
                    .filter(i -> !occupiedPositions.contains(i))
                    .forEach(i -> availablePositions.add(i));
                if (!availablePositions.isEmpty()) {
                    final int collectiblePos = availablePositions.get(random.nextInt(availablePositions.size()));
                    // 80% coin, 20% seconda vita
                    CollectibleType type = random.nextDouble() < 0.1 ? CollectibleType.SECOND_LIFE : CollectibleType.COIN;
                    final Collectible collectible = new CollectibleImpl(collectiblePos, chunk.getPosition(), type);
                    chunk.addObjectAt(collectible, collectiblePos);
                }
            }
        }
    }

}
