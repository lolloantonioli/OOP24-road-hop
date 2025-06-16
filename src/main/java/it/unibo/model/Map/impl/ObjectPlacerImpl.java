package it.unibo.model.Map.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.ObjectPlacer;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

public final class ObjectPlacerImpl implements ObjectPlacer {

    private final List<List<Integer>> patterns;
    private final Random random;
    private int lastSafeCell = -1;

    private static final double SAFE_CELL_PROBABILITY = 0.4;
    private static final double SECOND_LIFE_PROBABILITY = 0.1;
    
    /**
     * Creates a new {@code ObjectPlacerImpl} instance.
     * Initializes the patterns for placing obstacles.
     */
    public ObjectPlacerImpl() {
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
        final int safeCell;
        if (lastSafeCell == -1) {
            safeCell = random.nextInt(ChunkImpl.CELLS_PER_ROW);
        } else {
            final int min = Math.max(0, lastSafeCell - 1);
            final int max = Math.min(ChunkImpl.CELLS_PER_ROW - 1, lastSafeCell + 1);
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
        if (random.nextDouble() < SAFE_CELL_PROBABILITY) {
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
                final CollectibleType type = random.nextDouble() < SECOND_LIFE_PROBABILITY ? CollectibleType.SECOND_LIFE : CollectibleType.COIN;
                final Collectible collectible = new CollectibleImpl(collectiblePos, chunk.getPosition(), type);
                chunk.addObjectAt(collectible, collectiblePos);
            }
        }
    }

}
