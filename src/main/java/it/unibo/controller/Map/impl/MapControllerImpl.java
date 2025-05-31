package it.unibo.controller.Map.impl;

import java.awt.Color;
import java.util.List;

import it.unibo.controller.Map.api.MapController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.view.GamePanel;

public class MapControllerImpl implements MapController {

    private final GameMap mapModel;
    private final GamePanel view;

    private static final int INITIAL_SPEED = 1;

    public MapControllerImpl(final GamePanel view) {
        this.view = view;
        this.mapModel = new GameMapImpl(INITIAL_SPEED);
    }

    @Override
    public void updateMap() {
        mapModel.update();
    }

    @Override
    public List<Chunk> getVisibleChunks() {
        return mapModel.getVisibleChunks();
    }

    @Override
    public void increaseScrollSpeed() {
        mapModel.increaseScrollSpeed();
    }

    @Override
    public int getCurrentPosition() {
        return mapModel.getCurrentPosition();
    }

    @Override
    public int getScrollSpeed() {
        return mapModel.getScrollSpeed();
    }

    @Override
    public void setAnimationOffset(final int offset) {
        view.setAnimationOffset(offset);
    }

    @Override
    public void updateView() {
        view.refresh();
    }

    @Override
    public int getCellHeight() {
        return view.getCellHeight();
    }

    @Override
    public int getChunksNumber() {
        return GameMapImpl.CHUNKS_NUMBER;
    }

    @Override
    public int getCellsPerRow() {
        return ChunkImpl.CELLS_PER_ROW;
    }

    @Override
    public Color getChunkBackgroundColor(final int chunkIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        final List<Chunk> chunks = getVisibleChunks();
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
    public boolean hasCellObject(final int chunkIndex, final int cellIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        //checkArgument(cellIndex >= 0 && cellIndex < getCellsPerRow(), "Cell index out of bounds: " + cellIndex);
        final List<Chunk> chunks = getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        return chunk.getCellAt(cellIndex).hasObject();
    }

    @Override
    public Color getCellObjectColor(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        final GameObject obj = chunk.getCellAt(cellIndex).getContent().orElse(null);
        if (obj instanceof Collectible collectible) {
            if (collectible.getType() == CollectibleType.SECOND_LIFE) {
                return Color.MAGENTA;
            } else {
                return Color.YELLOW;
            }
        }
        return Color.BLACK;
    }

    @Override
    public boolean isCellObjectCircular(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        final GameObject obj = chunk.getCellAt(cellIndex).getContent().orElse(null);
        return obj instanceof Collectible;
    }

}
