package it.unibo.model.Map.impl;

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
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }
        if (type == null) {
            throw new IllegalArgumentException("ChunkType cannot be null");
        }
        this.position = position;
        this.type = type;
        this.cells = IntStream.range(0, CELLS_PER_ROW)
            .mapToObj(x -> new CellImpl(x, position))
            .collect(Collectors.toList());
    }

    @Override
    public boolean addObjectAt(final GameObject obj, final int cellX) {
        if (obj == null) {
            throw new IllegalArgumentException("GameObject cannot be null");
        }
        if (cellX < 0 || cellX >= CELLS_PER_ROW) {
            throw new IllegalArgumentException("Cell index out of bounds");
        }
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
        return this.cells;
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
