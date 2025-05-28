package it.unibo.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.controller.Map.api.MapController;
import it.unibo.model.Map.api.Cell;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.impl.ChunkImpl;
import it.unibo.model.Map.impl.GameMapImpl;
import it.unibo.model.Map.util.ChunkType;

public class GamePanel extends JPanel {

    private final MapController controller;
    private final int chunksNumber;
    private final int cellsPerRow;

    public GamePanel(final MapController controller) {
        this.controller = controller;
        this.chunksNumber = GameMapImpl.CHUNKS_NUMBER;
        this.cellsPerRow = ChunkImpl.CELLS_PER_ROW;
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final List<Chunk> chunks = controller.getVisibleChunks();
        final int cellWidth = getWidth() / cellsPerRow;
        final int cellHeight = getHeight() / chunksNumber;

        for (int row = 0; row < chunks.size(); row++) {
            final Chunk chunk = chunks.get(row);
            final List<Cell> cells = chunk.getCells();
            for (int col = 0; col < cells.size(); col++) {
                final int x = col * cellWidth;
                final int y = row * cellHeight;
                drawCell(g, x, y, cellWidth, cellHeight, chunk, cells.get(col));
            }
        }
    }

    private void drawCell(final Graphics g, final int x, final int y, final int cellWidth,
        final int cellHeight, final Chunk chunk, final Cell cell) {
        g.setColor(getColorForChunk(chunk));
        g.fillRect(x, y, cellWidth, cellHeight);
        cell.getContent().ifPresent(obj -> {
            g.setColor(Color.BLACK);
            g.fillOval(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
        });
        g.setColor(Color.BLACK);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private Color getColorForChunk(final Chunk chunk) {
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

    public void refresh() {
        repaint();
    }
    
}
