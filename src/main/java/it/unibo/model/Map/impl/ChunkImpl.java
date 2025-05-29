package it.unibo.model.Map.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;

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

        int width = obj.getWidthInCells();
        
        // controlla se c'è spazio per l'oggetto
        if (cellX + width > CELLS_PER_ROW) {
            return false; 
        }
        
        // controlla se tutte le celle che servono sono disponibili
        if (!isCellRangeAvailable(cellX, width)) {
            return false;
        }

        return cells.get(cellX).addObject(obj);
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

    @Override
    public boolean isCellAvailable(int cellX) {
        if (cellX < 0 || cellX >= CELLS_PER_ROW) {
            return false;
        }
        
        Cell cell = getCellAt(cellX);
        
        if (cell.hasObject()) {
            return false;
        }
        
        // controlla se un oggetto multicella occupa la cella
        for (GameObject obj : getObjects()) {
            if (obj.occupiesCell(cellX)) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public boolean isCellRangeAvailable(int startCellX, int width) {
        for (int i = 0; i < width; i++) {
            int cellX = startCellX + i;
            if (cellX < 0 || cellX >= getCells().size() || !isCellAvailable(cellX)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeObject(GameObject obj) {
        //trova l'oggetto
        for (Cell cell : getCells()) {
            if (cell.hasObject() && cell.getContent().orElse(null) == obj) {
                cell.removeObject();
                return true;
            }
        }
        return false;}

}
