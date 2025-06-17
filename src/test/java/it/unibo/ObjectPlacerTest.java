package it.unibo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.impl.ChunkFactoryImpl;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.api.Obstacle;

class ObjectPlacerTest {

    private final static int NUM_CHUNKS = 1_000;
    private final static int N = ChunkImpl.CELLS_PER_ROW;

    private List<Chunk> chunks;

    @BeforeEach
    void setUp() {
        ChunkFactory factory = new ChunkFactoryImpl();
        chunks = IntStream.range(0, NUM_CHUNKS)
                          .mapToObj(factory::createGrassChunk)
                          .collect(Collectors.toList());
    }

    @Test
    void testPath() {
        final int startRow = 2;
        final int startCol = 4;

        Set<Integer> current = Set.of(startCol);

        for (int row = startRow + 1; row < NUM_CHUNKS; row++) {
            final Set<Integer> next = new HashSet<>();
            final Chunk chunk = chunks.get(row);

            for (final int col : current) {
                for (int d = -1; d <= 1; d++) {
                    final int nc = col + d;
                    if (nc >= 0 && nc < N && isSafe(chunk, nc)) {
                        next.add(nc);
                    }
                }
            }

            assertFalse(next.isEmpty(), "Blocco completo alla riga " + row);
            current = next;
        }

        assertFalse(current.isEmpty(), "Non esiste un percorso dalla cella di partenza all'ultima riga");
    }

    private boolean isSafe(final Chunk chunk, final int col) {
        return chunk.getCellAt(col).getContent()
                    .stream()
                    .noneMatch(o -> o instanceof Obstacle);
    }

    @Test
    void testPlaceObstaclesLeavesSafeCell() {
        var placer = new it.unibo.model.Map.impl.ObjectPlacerImpl();
        var chunk = new it.unibo.model.Map.impl.ChunkImpl(0, it.unibo.model.Map.util.ChunkType.GRASS);
        placer.placeObstacles(chunk);

        // Deve esserci almeno una cella senza ostacolo
        boolean atLeastOneSafe = IntStream.range(0, ChunkImpl.CELLS_PER_ROW)
            .anyMatch(col -> chunk.getCellAt(col).getContent().stream()
                .noneMatch(o -> o instanceof it.unibo.model.Map.api.Obstacle));
        assertTrue(atLeastOneSafe, "Deve esserci almeno una cella senza ostacolo");
    }

    @Test
    void testPlaceObstaclesDoesNotPlaceOnSafeCell() {
        var placer = new it.unibo.model.Map.impl.ObjectPlacerImpl();
        var chunk = new it.unibo.model.Map.impl.ChunkImpl(0, it.unibo.model.Map.util.ChunkType.GRASS);
        placer.placeObstacles(chunk);

        // Trova la safe cell (quella senza ostacolo)
        int safeCell = IntStream.range(0, ChunkImpl.CELLS_PER_ROW)
            .filter(col -> chunk.getCellAt(col).getContent().stream()
                .noneMatch(o -> o instanceof it.unibo.model.Map.api.Obstacle))
            .findFirst().orElse(-1);

        assertNotEquals(-1, safeCell, "Safe cell non trovata");
        // Verifica che non ci sia ostacolo nella safe cell
        assertTrue(chunk.getCellAt(safeCell).getContent().stream()
            .noneMatch(o -> o instanceof it.unibo.model.Map.api.Obstacle));
    }

    @Test
    void testPlaceCollectiblesOnlyOnFreeCell() {
        var placer = new it.unibo.model.Map.impl.ObjectPlacerImpl();
        var chunk = new it.unibo.model.Map.impl.ChunkImpl(0, it.unibo.model.Map.util.ChunkType.GRASS);
        placer.placeObstacles(chunk);
        placer.placeCollectibles(chunk);

        // Se c'è un collectible, deve essere in una cella senza ostacolo
        IntStream.range(0, ChunkImpl.CELLS_PER_ROW).forEach(col -> {
            boolean hasCollectible = chunk.getCellAt(col).getContent().stream()
                .anyMatch(o -> o instanceof it.unibo.model.Map.api.Collectible);
            boolean hasObstacle = chunk.getCellAt(col).getContent().stream()
                .anyMatch(o -> o instanceof it.unibo.model.Map.api.Obstacle);
            if (hasCollectible) {
                assertFalse(hasObstacle, "Un collectible non può stare su una cella con ostacolo");
            }
        });
    }
}
