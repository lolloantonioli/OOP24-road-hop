package it.unibo.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import it.unibo.controller.Map.api.MapController;

public class GamePanel extends JPanel {

    private MapController controller;
    private int chunksNumber;
    private int cellsPerRow;
    private int animationOffset = 0;

    public void setController(final MapController controller) {
        this.controller = controller;
        this.chunksNumber = controller.getChunksNumber();
        this.cellsPerRow = controller.getCellsPerRow();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final int cellWidth = getWidth() / cellsPerRow;
        final int cellHeight = getHeight() / chunksNumber;

        for (int row = 0; row < chunksNumber + 1; row++) {
            for (int col = 0; col < cellsPerRow; col++) {
                final int x = col * cellWidth;
                final int y = (chunksNumber - 1 - row) * cellHeight + animationOffset;
                drawCell(g, x, y, cellWidth, cellHeight, row, col);
            }
        }
    }

    private void drawCell(final Graphics g, final int x, final int y, final int cellWidth, final int cellHeight, final int chunkIndex, final int cellIndex) {
        g.setColor(controller.getChunkBackgroundColor(chunkIndex));
        g.fillRect(x, y, cellWidth, cellHeight);
        if (controller.hasCellObject(chunkIndex, cellIndex)) {
            g.setColor(controller.getCellObjectColor(chunkIndex, cellIndex));
            if (controller.isCellObjectCircular(chunkIndex, cellIndex)) {
                g.fillOval(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
            } else {
                g.fillRect(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
            }
        }
        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    public void refresh() {
        repaint();
    }

    public void setAnimationOffset(final int offset) {
        this.animationOffset = offset;
    }

    public int getCellHeight() {
        return getHeight() / chunksNumber;
    }
    
}
