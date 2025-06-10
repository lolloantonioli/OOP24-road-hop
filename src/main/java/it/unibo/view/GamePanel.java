package it.unibo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public class GamePanel extends JPanel {

    private MapController controller;
    private MovingObstacleController obstacleController;
    private int chunksNumber;
    private int cellsPerRow;
    private int animationOffset = 0;
    private Optional<Integer> countdownValue = Optional.empty();

    public void setController(final MapController controller, final MovingObstacleController obstacleController) {
        this.controller = controller;
        this.obstacleController = obstacleController;
        this.chunksNumber = controller.getChunksNumber();
        this.cellsPerRow = controller.getCellsPerRow();
    }

    public void showCountdown(final int value) {
        this.countdownValue = Optional.of(value);
        repaint();
    }

    public void hideCountdown() {
        this.countdownValue = Optional.empty();
        repaint();
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

        // Disegna gli ostacoli mobili
        if (obstacleController != null) {
            drawMovingObstacles(g, cellWidth, cellHeight);
        }

        if (countdownValue.isPresent()) {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, getWidth() / 5));
            String text = countdownValue.get() > 0 ? String.valueOf(countdownValue.get()) : "GO!";
            int textWidth = g.getFontMetrics().stringWidth(text);
            int textHeight = g.getFontMetrics().getAscent();
            g.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
        }
    }

    private void drawMovingObstacles(final Graphics g, final int cellWidth, final int cellHeight) {
        List<MovingObstacles> obstacles = obstacleController.getAllObstacles();
        List<Chunk> visibleChunks = controller.getGameMap().getVisibleChunks();

        for (MovingObstacles obstacle : obstacles) {
            // Trova l'indice relativo del chunk visibile
            int screenY = -1;
            for (int i = 0; i < visibleChunks.size(); i++) {
                if (visibleChunks.get(i).getPosition() == obstacle.getY()) {
                    screenY = i;
                    break;
                }
            }

            int screenX = (int) obstacle.getX();
            int pixelX = screenX * cellWidth;
            int pixelY = (chunksNumber - screenY - 1) * cellHeight + animationOffset;

            int pixelWidth = obstacle.getWidthInCells() * cellWidth;
            if (pixelX + pixelWidth > 0 && pixelX < getWidth()) {
                drawObstacle(g, obstacle, pixelX, pixelY, cellWidth, cellHeight);
            }
        }
    }

    private void drawObstacle(final Graphics g, final MovingObstacles obstacle, final int x, final int y, final int cellWidth, final int cellHeight) {
        if (!obstacle.isVisible()) {
            return;
        }
        ObstacleType type = obstacle.getType();
        int widthInCells = obstacle.getWidthInCells();
        int ox = obstacle.getX();

        // Calcola la parte effettivamente visibile nella griglia
        int visibleStart = Math.max(0, ox);
        int visibleEnd = Math.min(cellsPerRow, ox + widthInCells);
        int visibleCells = visibleEnd - visibleStart;

        if (visibleCells <= 0) {
            return;
        }

        // NON FUNZIONAAAAA, MA IL PROBLEMA E' QUI !!!
        int pixelX = x; 
        int pixelWidth = visibleCells * cellWidth;
        
        if (type == ObstacleType.CAR) {
            g.setColor(Color.RED);
            g.fillRect(pixelX, y + cellHeight / 4, pixelWidth, cellHeight / 2);
        } else if (type == ObstacleType.TRAIN) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(pixelX, y + cellHeight / 6, pixelWidth, cellHeight * 2 / 3);
        } else if (type == ObstacleType.LOG) {
            g.setColor(Color.ORANGE);
            g.fillRect(pixelX, y + cellHeight / 3, pixelWidth, cellHeight / 3);
        }
    }

    private void drawCell(final Graphics g, final int x, final int y, final int cellWidth, final int cellHeight, final int chunkIndex, final int cellIndex) {
        g.setColor(controller.getChunkColor(chunkIndex));
        g.fillRect(x, y, cellWidth, cellHeight);
        if (controller.hasCellObjects(chunkIndex, cellIndex)) {
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
