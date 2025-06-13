package it.unibo;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.ChunkFactory;
import it.unibo.model.Map.api.ObjectPlacer;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.impl.ChunkFactoryImpl;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.ObjectPlacerImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class ObjectPlacerTest {

    private ObjectPlacer placer;
    private ChunkFactory factory;
    private List<Chunk> chunks;

    private static final int CELLS_PER_ROW = ChunkImpl.CELLS_PER_ROW;
    private static final int NUM_CHUNKS = 1000;

    @BeforeEach
    void setUp() {
        this.placer = new ObjectPlacerImpl();
        this.factory = new ChunkFactoryImpl();
        this.chunks = new ArrayList<>();
    }

    @Test
    void testSafePathExistsInManyGrassChunks() {
        // Genera tanti chunk GRASS con ostacoli piazzati
        for (int i = 0; i < NUM_CHUNKS; i++) {
            chunks.add(factory.createGrassChunk(i));
        }

        // Trova la posizione sicura nella prima riga (dove non c'è ostacolo)
        Set<Integer> safeStarts = new HashSet<>();
        for (int col = 0; col < CELLS_PER_ROW; col++) {
            if (chunks.get(0).getCellAt(col).getContent().stream().noneMatch(obj -> obj instanceof Obstacle)) {
                safeStarts.add(col);
            }
        }
        assertFalse(safeStarts.isEmpty(), "Deve esserci almeno una cella sicura nella prima riga");

        // BFS per verificare che almeno una colonna sicura arrivi fino in fondo
        Set<Integer> currentSafe = new HashSet<>(safeStarts);
        for (int row = 1; row < NUM_CHUNKS; row++) {
            Set<Integer> nextSafe = new HashSet<>();
            for (int col : currentSafe) {
                for (int d = -1; d <= 1; d++) { // muovi a sinistra, centro, destra
                    int nextCol = col + d;
                    if (nextCol >= 0 && nextCol < CELLS_PER_ROW) {
                        boolean blocked = chunks.get(row).getCellAt(nextCol).getContent()
                            .stream().anyMatch(obj -> obj instanceof Obstacle);
                        if (!blocked) {
                            nextSafe.add(nextCol);
                        }
                    }
                }
            }
            assertFalse(nextSafe.isEmpty(), "Deve esserci almeno una cella sicura alla riga " + row);
            currentSafe = nextSafe;
        }
        assertFalse(currentSafe.isEmpty(), "Deve esserci almeno un percorso sicuro fino all'ultima riga");
    }
}