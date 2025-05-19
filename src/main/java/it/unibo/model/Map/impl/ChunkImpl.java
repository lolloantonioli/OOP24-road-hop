package it.unibo.model.Map.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.GameObject;
import it.unibo.model.Map.util.ChunkType;

public class ChunkImpl implements Chunk {

    private final List<Cell> cells;
    private final ChunkType type;
    private final int position;

    public static final int CELLS_PER_ROW = 9;

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
        return (cellX >= 0 && cellX < CELLS_PER_ROW) && cells.get(cellX).addObject(obj);
    }

    @Override
    public List<GameObject> getObjects() {
        return cells.stream()
            .filter(Cell::hasObject)
            .map(Cell::getContent)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    @Override
    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    @Override
    public Cell getCellAt(final int cellX) {
        return (cellX >= 0 && cellX < CELLS_PER_ROW) ? cells.get(cellX) : null;
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
