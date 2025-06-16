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
        final int startCol = N / 2;

        assertTrue(isSafe(chunks.get(startRow), startCol),
                   "La cella centrale del terzo chunk (riga 2, col " + startCol + ") deve essere libera");

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
}
