package it.unibo.controller.Map.impl;

import java.awt.Color;
import java.util.List;

import it.unibo.controller.Map.api.MapAdapter;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;

/**
 * Implementation of the MapController interface.
 */
public class MapAdapterImpl implements MapAdapter {

    private final GameMap gameMap;

    public MapAdapterImpl(final GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public Color getChunkColor(final int chunkIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        final ChunkType type = chunk.getType();
        if (type == ChunkType.GRASS) {
            return Color.GREEN;
        } else if (type == ChunkType.RIVER) {
            return Color.BLUE;
        } else if (type == ChunkType.ROAD) {
            return Color.BLACK;
        } else {
            return Color.GRAY;
        }
    }

    @Override
    public boolean hasCellObjects(final int chunkIndex, final int cellIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        //checkArgument(cellIndex >= 0 && cellIndex < getCellsPerRow(), "Cell index out of bounds: " + cellIndex);
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        return chunk.getCellAt(cellIndex).hasObject();
    }

    @Override
    public Color getCellObjectColor(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        // Prendi il primo oggetto "visibile" per la view (ad esempio un Collectible, altrimenti il primo oggetto)
        return chunk.getCellAt(cellIndex).getContent().stream()
            .filter(obj -> obj instanceof Collectible)
            .findFirst()
            .map(obj -> {
                Collectible collectible = (Collectible) obj;
                if (collectible.getType() == CollectibleType.SECOND_LIFE) {
                    return Color.MAGENTA;
                } else {
                    return Color.YELLOW;
                }
            })
            .orElse(Color.BLACK);
    }

    @Override
    public boolean isCellObjectCircular(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        // Se almeno un oggetto è un Collectible, la cella è circolare
        return chunk.getCellAt(cellIndex).getContent().stream()
            .anyMatch(obj -> obj instanceof Collectible);
    }

}
