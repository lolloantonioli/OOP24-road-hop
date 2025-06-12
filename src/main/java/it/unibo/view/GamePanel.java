package it.unibo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.controller.GameController;
import it.unibo.controller.Map.api.MapController;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.Chunk;
import it.unibo.model.Map.api.Collectible;
import it.unibo.model.Map.util.ChunkType;
import it.unibo.model.Map.util.CollectibleType;
import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public class GamePanel extends JPanel {

    private MovingObstacleController obstacleController;
    private GameController gameController;
    private int chunksNumber;
    private int cellsPerRow;
    private int animationOffset = 0;
    private Optional<Integer> countdownValue = Optional.empty();

    public void setController(final GameController gameController) {
        this.gameController = gameController;
        this.obstacleController = gameController.getObstacleController();
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
        List<Chunk> visibleChunks = gameController.getGameMap().getVisibleChunks();

        for (MovingObstacles obstacle : obstacles) {
            // Trova l'indice relativo del chunk visibile
            int screenY = -1;
            for (int i = 0; i < visibleChunks.size(); i++) {
                if (visibleChunks.get(i).getPosition() == obstacle.getY()) {
                    screenY = i;
                    break;
                }
            }

            if (screenY == -1) continue; // Chunk non visibile

            int obstacleX = (int) obstacle.getX();
            int obstacleWidth = obstacle.getWidthInCells();
            
            // Calcola i limiti di visibilità corretti
            int leftBound = Math.max(0, obstacleX);
            int rightBound = Math.min(cellsPerRow, obstacleX + obstacleWidth);
            
            // Se l'ostacolo è completamente fuori dai bounds, non disegnarlo
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
        
        // Calcola la posizione e dimensione in pixel
        int pixelX = leftBound * cellWidth;
        int visibleCells = rightBound - leftBound;
        int pixelWidth = visibleCells * cellWidth;
        
        // Se l'ostacolo inizia prima del bordo sinistro, dobbiamo adjustare il rendering
        if (obstacleX < 0) {
            // L'ostacolo si estende oltre il bordo sinistro
            int offsetCells = -obstacleX; // Quante celle sono fuori dal bordo
            pixelX = 0; // Inizia dal bordo sinistro dello schermo
            pixelWidth = Math.min(obstacle.getWidthInCells() - offsetCells, cellsPerRow) * cellWidth;
        }
        
        // Assicurati che non disegniamo oltre i bordi dello schermo
        if (pixelX + pixelWidth > getWidth()) {
            pixelWidth = getWidth() - pixelX;
        }
        
        if (pixelWidth <= 0) {
            return;
        }
        
        // Disegna l'ostacolo con il colore appropriato
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
        g.setColor(getChunkColor(chunkIndex));
        g.fillRect(x, y, cellWidth, cellHeight);
        if (hasCellObjects(chunkIndex, cellIndex)) {
            g.setColor(getCellObjectColor(chunkIndex, cellIndex));
            if (isCellObjectCircular(chunkIndex, cellIndex)) {
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

    public Color getChunkColor(final int chunkIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        final List<Chunk> chunks = gameController.getGameMap().getVisibleChunks();
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

    public boolean hasCellObjects(final int chunkIndex, final int cellIndex) {
        //checkArgument(chunkIndex >= 0 && chunkIndex < getChunksNumber(), "Chunk index out of bounds: " + chunkIndex);
        //checkArgument(cellIndex >= 0 && cellIndex < getCellsPerRow(), "Cell index out of bounds: " + cellIndex);
        final List<Chunk> chunks = gameController.getGameMap().getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        return chunk.getCellAt(cellIndex).hasObject();
    }

    public Color getCellObjectColor(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = gameController.getGameMap().getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        // Prendi il primo oggetto "visibile" per la view (ad esempio un Collectible, altrimenti il primo oggetto)
        return chunk.getCellAt(cellIndex).getContent().stream()
            .filter(obj -> obj instanceof Collectible)
            .findFirst()
            .map(obj -> {
                Collectible collectible = (Collectible) obj;
                if (collectible.getType() == CollectibleType.SECOND_LIFE) {
                    return Color.MAGENTA;
                } else {
                    return Color.YELLOW;
                }
            })
            .orElse(Color.BLACK);
    }

    public boolean isCellObjectCircular(final int chunkIndex, final int cellIndex) {
        //checkState((chunkIndex >= 0 && chunkIndex < getChunksNumber()) || (cellIndex >= 0 && cellIndex < getCellsPerRow()),
        //"This method should called only after 'hasCellBobject' check");
        final List<Chunk> chunks = gameController.getGameMap().getVisibleChunks();
        final Chunk chunk = chunks.get(chunkIndex);
        // Se almeno un oggetto è un Collectible, la cella è circolare
        return chunk.getCellAt(cellIndex).getContent().stream()
            .anyMatch(obj -> obj instanceof Collectible);
    }
    
}
