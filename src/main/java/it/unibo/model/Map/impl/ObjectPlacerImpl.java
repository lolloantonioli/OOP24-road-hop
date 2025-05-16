package it.unibo.model.Map.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.ObjectPlacer;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;

public class ObjectPlacerImpl implements ObjectPlacer {

    private final List<List<Integer>> patterns;
    private final Random random;

    public ObjectPlacerImpl() {
        this.patterns = new ArrayList<>();
        this.random = new Random();
        // Aggiungiamo diversi pattern che garantiscono un percorso
        patterns.add(List.of(0, 2, 4));    // Pattern 1: alberi nelle posizioni 0, 2 e 4
        patterns.add(List.of(1, 3, 5));    // Pattern 2: alberi nelle posizioni 1, 3 e 5
        patterns.add(List.of(0, 3, 6));    // Pattern 3: alberi nelle posizioni 0, 3 e 6
        patterns.add(List.of(1, 4, 7));    // Pattern 4: alberi nelle posizioni 1, 4 e 7
        patterns.add(List.of(2, 5, 8));    // Pattern 5: alberi nelle posizioni 2, 5 e 8
        patterns.add(List.of(0, 4, 8));    // Pattern 6: alberi diagonali
        patterns.add(List.of(0, 2));       // Pattern 7: solo due alberi
        patterns.add(List.of(5, 7));       // Pattern 8: solo due alberi in posizione diversa
        patterns.add(List.of());           // Pattern 9: nessun albero
    }

    @Override
    public void placeObstacles(final Chunk chunk) {
        final int patternIndex = random.nextInt(patterns.size());
        final List<Integer> selectedPattern = patterns.get(patternIndex);
        for (Integer pos : selectedPattern) {
            if (pos < ChunkImpl.CELLS_PER_ROW) {
                Obstacle tree = new ObstacleImpl(pos, chunk.getPosition(), ObstacleType.TREE, false);
                chunk.addObjectAt(tree, pos);
            }
        }
    }

    @Override
    public void placeCollectibles(final Chunk chunk) {
        // Probabilità del 40% di aggiungere un collezionabile nel chunk di erba
        if (random.nextDouble() < 0.4) {
            // Trova le posizioni già occupate dagli alberi
            Set<Integer> occupiedPositions = new HashSet<>();
            for (var object : chunk.getObjects()) {
                if (object instanceof Obstacle) {
                    occupiedPositions.add(object.getX());
                }
            }
            
            // Se ci sono posizioni libere, aggiungi un collezionabile
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < ChunkImpl.CELLS_PER_ROW; i++) {
                if (!occupiedPositions.contains(i)) {
                    availablePositions.add(i);
                }
            }
            
            if (!availablePositions.isEmpty()) {
                // Scegli una posizione casuale tra quelle disponibili
                int collectiblePos = availablePositions.get(random.nextInt(availablePositions.size()));
                
                // Crea e aggiungi il collezionabile
                Collectible coin = new CollectibleImpl(
                    collectiblePos, 
                    chunk.getPosition(), 
                    CollectibleType.COIN
                );
                chunk.addObjectAt(coin, collectiblePos);
            }
        }
    }

}
