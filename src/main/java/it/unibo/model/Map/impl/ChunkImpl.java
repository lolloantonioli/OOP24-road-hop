package it.unibo.model.Map.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.util.ChunkType;

/**
 * Implementation of the {@code Chunk} interface.
 * Represents a chunk of the game map, which is a row that can contain multiple {@code Cell} and {@code GameObject}.
 */
public final class ChunkImpl implements Chunk {

    /**
     * The number of cells in a chunk row.
     */
    public static final int CELLS_PER_ROW = 9;

    private final List<Cell> cells;
    private final ChunkType type;
    private final int position;

    /**
     * Constructs a new {@code ChunkImpl} with the specified position and type.
     *
     * @param position the x-coordinate of the chunk.
     * @param type the type of the chunk (e.g., road, railway, river, grass).
     * @throws IllegalArgumentException if position is negative.
     * @throws NullPointerException if type is null.
     */
    public ChunkImpl(final int position, final ChunkType type) {
        checkArgument(position >= 0, "Position must be non-negative");
        this.position = position;
        this.type = checkNotNull(type, "ChunkType cannot be null"); // mettere nella doc la dicitura @throws NullPointerException
        this.cells = IntStream.range(0, CELLS_PER_ROW)
            .mapToObj(x -> new CellImpl(x, position))
            .collect(Collectors.toList());
    }

    @Override
    public boolean addObjectAt(final GameObject obj, final int cellX) {
        checkArgument(cellX >= 0 && cellX < CELLS_PER_ROW, "Cell index out of bounds");
        checkNotNull(obj, "GameObject cannot be null");
        return cells.get(cellX).addObject(obj);
    }

    @Override
    public List<GameObject> getObjects() {
        return cells.stream()
            .flatMap(cell -> cell.getContent().stream())
            .collect(Collectors.toList());
    }

    @Override
    public List<Cell> getCells() {
        return ImmutableList.copyOf(cells);
    }

    @Override
    public Cell getCellAt(final int cellX) {
        checkArgument(cellX >= 0 && cellX < CELLS_PER_ROW, "Cell index out of bounds");
        return cells.get(cellX);
    }

    @Override
    public ChunkType getType() {
        return this.type;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

}
