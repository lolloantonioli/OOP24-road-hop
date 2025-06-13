package it.unibo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.controller.GameControllerImpl;
import it.unibo.controller.Map.api.MapFormatter;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public class GamePanel extends JPanel {

    private MovingObstacleController obstacleController;
    private GameControllerImpl gameController;
    private MapFormatter mapAdapter;
    private int chunksNumber;
    private int cellsPerRow;
    private int animationOffset = 0;
    private Optional<Integer> countdownValue = Optional.empty();

    public void setController(final GameControllerImpl gameController) {
        this.gameController = gameController;
        this.obstacleController = gameController.getObstacleController();
        this.mapAdapter = gameController.getMapFormatter();
        this.chunksNumber = gameController.getMapHeight();
        this.cellsPerRow = gameController.getMapWidth();
        addKeyListener(gameController);
        setFocusable(true);
        requestFocusInWindow();
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

        if (obstacleController != null) {
            drawMovingObstacles(g, cellWidth, cellHeight);
        }

        if (countdownValue.isPresent()) {
            drawCountdown(g);
        }
    }

    private void drawCountdown(final Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, getWidth() / 5));
        final String text = countdownValue.get() > 0 ? String.valueOf(countdownValue.get()) : "GO!";
        final int textWidth = g.getFontMetrics().stringWidth(text);
        final int textHeight = g.getFontMetrics().getAscent();
        g.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
    }

    private void drawMovingObstacles(final Graphics g, final int cellWidth, final int cellHeight) {
        List<MovingObstacles> obstacles = obstacleController.getAllObstacles();
        List<Chunk> visibleChunks = gameController.getGameMap().getVisibleChunks();

        for (MovingObstacles obstacle : obstacles) {
            int screenY = -1;
            for (int i = 0; i < visibleChunks.size(); i++) {
                if (visibleChunks.get(i).getPosition() == obstacle.getY()) {
                    screenY = i;
                    break;
                }
            }

            if (screenY == -1) continue;

            int obstacleX = (int) obstacle.getX();
            int obstacleWidth = obstacle.getWidthInCells();
            
            int leftBound = Math.max(0, obstacleX);
            int rightBound = Math.min(cellsPerRow, obstacleX + obstacleWidth);
            
            if (leftBound >= rightBound || rightBound <= 0 || leftBound >= cellsPerRow) {
                continue;
            }
            
            int pixelY = (chunksNumber - screenY - 1) * cellHeight + animationOffset;
            
            drawObstacle(g, obstacle, obstacleX, pixelY, cellWidth, cellHeight, leftBound, rightBound);
        }
    }

    private void drawObstacle(final Graphics g, final MovingObstacles obstacle, final int obstacleX, 
        final int y, final int cellWidth, final int cellHeight, 
        final int leftBound, final int rightBound) {
        if (!obstacle.isVisible()) {
            return;
        }
        
        ObstacleType type = obstacle.getType();
        
        int pixelX = leftBound * cellWidth;
        int visibleCells = rightBound - leftBound;
        int pixelWidth = visibleCells * cellWidth;
        
        if (obstacleX < 0) {
            int offsetCells = -obstacleX;
            pixelX = 0;
            pixelWidth = Math.min(obstacle.getWidthInCells() - offsetCells, cellsPerRow) * cellWidth;
        }
        
        if (pixelX + pixelWidth > getWidth()) {
            pixelWidth = getWidth() - pixelX;
        }
        
        if (pixelWidth <= 0) {
            return;
        }
        
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
        g.setColor(mapAdapter.getChunkColor(chunkIndex));
        g.fillRect(x, y, cellWidth, cellHeight);
        if (mapAdapter.hasCellObjects(chunkIndex, cellIndex)) {
            g.setColor(mapAdapter.getCellObjectColor(chunkIndex, cellIndex));
            if (mapAdapter.isCellObjectCircular(chunkIndex, cellIndex)) {
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
