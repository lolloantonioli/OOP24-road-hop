package it.unibo.controller.Map.impl;

import java.awt.Color;
import java.util.List;

import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;

/**
 * Implementation of the MapController interface.
 */
public final class MapFormatterImpl implements MapFormatter {

    private final GameMap gameMap;

    /**
     * Constructs a MapFormatterImpl with the specified GameMap.
     *
     * @param gameMap the GameMap to be used by this formatter
     */
    public MapFormatterImpl(final GameMap gameMap) {
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
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);

        final boolean hasCollectible = chunk.getCellAt(cellIndex).getContent().stream()
            .anyMatch(obj -> obj instanceof Collectible && !((Collectible)obj).isCollected());
        final boolean hasTree = chunk.getType() == ChunkType.GRASS
            && chunk.getCellAt(cellIndex).getContent().stream()
                .anyMatch(obj -> obj instanceof Obstacle);

        return hasCollectible || hasTree;
    }

    @Override
    public Color getCellObjectColor(final int chunkIndex, final int cellIndex) {
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);

        final var collectible = chunk.getCellAt(cellIndex).getContent().stream()
            .filter(obj -> obj instanceof Collectible && !((Collectible)obj).isCollected())
            .map(obj -> (Collectible) obj)
            .findFirst();

        if (collectible.isPresent()) {
            if (collectible.get().getType() == CollectibleType.SECOND_LIFE) {
                return Color.MAGENTA;
            } else {
                return Color.YELLOW;
            }
        }

        if (chunk.getType() == ChunkType.GRASS) {
            final boolean hasTree = chunk.getCellAt(cellIndex).getContent().stream()
                .anyMatch(obj -> obj instanceof Obstacle);
            if (hasTree) {
                return Color.BLACK;
            }
        }

        throw new IllegalStateException("getCellObjectColor chiamato su cella senza oggetto da disegnare");
    }

    @Override
    public boolean isCellObjectCircular(final int chunkIndex, final int cellIndex) {
        final List<Chunk> chunks = gameMap.getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);

        return chunk.getCellAt(cellIndex).getContent().stream()
            .anyMatch(obj -> obj instanceof Collectible && !((Collectible)obj).isCollected());
    }

}
