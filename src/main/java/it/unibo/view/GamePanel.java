package it.unibo.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import it.unibo.controller.GameController;
import it.unibo.controller.map.api.MapFormatter;
import it.unibo.controller.obstacles.api.MovingObstacleController;
import it.unibo.model.map.api.Cell;
import it.unibo.model.map.api.Chunk;
import it.unibo.model.map.util.ObstacleType;
import it.unibo.model.obstacles.impl.MovingObstacles;
import it.unibo.model.player.api.Player;

/**
 * GamePanel is a custom JPanel that renders the game map, player, and moving obstacles.
 * It handles the drawing of cells, player sprite, and countdown timer.
 */
public final class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int DIV_FACTOR_FONT = 5;
    private static final int FONT_DIV_COUNTDOWN = 15;
    private static final int CONST_PADDING = 5;
    private static final int SCORE_PADDING = 10;
    private static final int PLAYER_RADIUS_DIV = 20;
    private static final int PLAYER_RADIUS_PADDING_DIV = 2;
    private static final int OBSTACLE_CAR_HEIGHT_DIV = 4;
    private static final int OBSTACLE_CAR_HEIGHT_MUL = 2;
    private static final int OBSTACLE_TRAIN_HEIGHT_DIV = 6;
    private static final int OBSTACLE_TRAIN_HEIGHT_MUL = 2;
    private static final int OBSTACLE_TRAIN_HEIGHT_DIV2 = 3;
    private static final int OBSTACLE_LOG_HEIGHT_DIV = 3;
    private static final int OBSTACLE_LOG_HEIGHT_DIV2 = 3;
    private static final int RAILWAY_BG_COLOR = 200;
    private static final int RAILWAY_RAIL_COLOR_R = 139;
    private static final int RAILWAY_RAIL_COLOR_G = 69;
    private static final int RAILWAY_RAIL_COLOR_B = 19;
    private static final int RAILWAY_TRAVERSE_COLOR = 120;
    private static final int RAILWAY_NUM_TRAVERSES = 2;
    private static final int RAILWAY_RAIL_WIDTH_DIV = 5;
    private static final int RAILWAY_RAIL_X_DIV1 = 6;
    private static final int RAILWAY_RAIL_X_MUL2 = 4;
    private static final int RAILWAY_TRAVERSE_HEIGHT_DIV = 8;
    private static final int BROWN_R = 139;
    private static final int BROWN_G = 69;
    private static final int BROWN_B = 19;

    private MovingObstacleController obstacleController;
    private GameController gameController;
    private MapFormatter mapFormatter;
    private int chunksNumber;
    private int cellsPerRow;
    private int animationOffset;
    private Optional<Integer> countdownValue = Optional.empty();

    /**
     * Sets the controllers and initializes the panel.
     * @param gameController the game controller to set
     */
    public void setController(final GameController gameController) {
        this.gameController = gameController;
        this.obstacleController = gameController.getObstacleController();
        this.mapFormatter = gameController.getMapFormatter();
        this.chunksNumber = gameController.getMapHeight();
        this.cellsPerRow = gameController.getMapWidth();
        addKeyListener(gameController);
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Shows the countdown timer on the panel.
     * @param value the countdown value to display
     */
    public void showCountdown(final int value) {
        this.countdownValue = Optional.of(value);
        repaint();
    }

    /**
     * Hides the countdown timer from the panel.
     */
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

        drawScore(g);

        if (gameController.getPlayerController().hasPlayerSecondLife()) {
            g.setColor(Color.MAGENTA);
            final int radius = getHeight() / PLAYER_RADIUS_DIV;
            final int padding = radius / PLAYER_RADIUS_PADDING_DIV;
            g.fillOval(padding, padding, radius, radius);
        }

        drawPlayer(g, cellWidth, cellHeight);

        if (countdownValue.isPresent()) {
            drawCountdown(g);
        }

    }

    private void drawScore(final Graphics g) {
        if (gameController == null || gameController.getPlayerController() == null) {
            return;
        }

        final int score = gameController.getPlayerController().getPlayerScore();
        final int coins = gameController.getPlayerController().getCollectedCoins();

        final String scoreText = String.valueOf(score);
        final String coinText = String.valueOf(coins);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, getHeight() / 10));
        int textWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, getWidth() - textWidth - SCORE_PADDING, g.getFont().getSize() + SCORE_PADDING);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, getHeight() / FONT_DIV_COUNTDOWN));
        textWidth = g.getFontMetrics().stringWidth(coinText);
        g.drawString(coinText, getWidth() - textWidth - SCORE_PADDING, g.getFont().getSize() * 3 + SCORE_PADDING + CONST_PADDING);
    }

    private void drawCountdown(final Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, getWidth() / DIV_FACTOR_FONT));
        final String text = countdownValue.get() > 0 ? String.valueOf(countdownValue.get()) : "GO!";
        final int textWidth = g.getFontMetrics().stringWidth(text);
        final int textHeight = g.getFontMetrics().getAscent();
        g.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
    }

    private void drawMovingObstacles(final Graphics g, final int cellWidth, final int cellHeight) {
        final List<MovingObstacles> obstacles = obstacleController.getAllObstacles();
        final List<Chunk> visibleChunks = gameController.getGameMap().getVisibleChunks();

        for (final MovingObstacles obstacle : obstacles) {
            int screenY = -1;
            for (int i = 0; i < visibleChunks.size(); i++) {
                if (visibleChunks.get(i).getPosition() == obstacle.getY()) {
                    screenY = i;
                    break;
                }
            }

            if (screenY == -1) {
                continue;
            }
            
            final int obstacleWidth = obstacle.getWidthInCells();

            final int leftBound = Math.max(0, obstacle.getX());
            final int rightBound = Math.min(cellsPerRow, obstacle.getX() + obstacleWidth);

            if (leftBound >= rightBound || rightBound <= 0 || leftBound >= cellsPerRow) {
                continue;
            }

            final int pixelY = (chunksNumber - screenY - 1) * cellHeight + animationOffset;

            drawObstacle(g, obstacle, obstacle.getX(), pixelY, cellWidth, cellHeight, leftBound, rightBound);
        }
    }

    private void drawObstacle(final Graphics g, final MovingObstacles obstacle, final int obstacleX, 
        final int y, final int cellWidth, final int cellHeight, 
        final int leftBound, final int rightBound) {
        if (!obstacle.isVisible()) {
            return;
        }

        final ObstacleType type = obstacle.getType();

        int pixelX = leftBound * cellWidth;
        final int visibleCells = rightBound - leftBound;
        int pixelWidth = visibleCells * cellWidth;

        if (obstacleX < 0) {
            final int offsetCells = -obstacleX;
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
            g.fillRect(pixelX, y + cellHeight / OBSTACLE_CAR_HEIGHT_DIV, pixelWidth, cellHeight / OBSTACLE_CAR_HEIGHT_MUL);
        } else if (type == ObstacleType.TRAIN) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(pixelX, y + cellHeight / OBSTACLE_TRAIN_HEIGHT_DIV,
            pixelWidth, cellHeight * OBSTACLE_TRAIN_HEIGHT_MUL / OBSTACLE_TRAIN_HEIGHT_DIV2);
        } else if (type == ObstacleType.LOG) {
            g.setColor(new Color(BROWN_R, BROWN_G, BROWN_B));
            g.fillRect(pixelX, y + cellHeight / OBSTACLE_LOG_HEIGHT_DIV, pixelWidth, cellHeight / OBSTACLE_LOG_HEIGHT_DIV2);
        }
    }

    private void drawCell(final Graphics g, final int x, final int y,
                          final int cellWidth, final int cellHeight,
                          final int chunkIndex, final int cellIndex) {
        if (mapFormatter.isRailwayCell(chunkIndex)) {
            g.setColor(new Color(RAILWAY_BG_COLOR, RAILWAY_BG_COLOR, RAILWAY_BG_COLOR));
            g.fillRect(x, y, cellWidth, cellHeight);

            g.setColor(new Color(RAILWAY_RAIL_COLOR_R, RAILWAY_RAIL_COLOR_G, RAILWAY_RAIL_COLOR_B));
            final int railWidth = cellWidth / RAILWAY_RAIL_WIDTH_DIV;
            g.fillRect(x + cellWidth / RAILWAY_RAIL_X_DIV1, y, railWidth, cellHeight);
            g.fillRect(x + cellWidth * RAILWAY_RAIL_X_MUL2 / RAILWAY_RAIL_X_DIV1, y, railWidth, cellHeight);

            g.setColor(new Color(RAILWAY_TRAVERSE_COLOR, RAILWAY_TRAVERSE_COLOR, RAILWAY_TRAVERSE_COLOR));
            final int numTraverses = RAILWAY_NUM_TRAVERSES;
            final int traverseHeight = cellHeight / RAILWAY_TRAVERSE_HEIGHT_DIV;
            for (int t = 1; t <= numTraverses; t++) {
                final int traverseY = y + t * cellHeight / (numTraverses + 1) - traverseHeight / 2;
                g.fillRect(x, traverseY, cellWidth, traverseHeight);
            }
        } else {
            g.setColor(mapFormatter.getChunkColor(chunkIndex));
            g.fillRect(x, y, cellWidth, cellHeight);
        }
        if (mapFormatter.hasCellObjects(chunkIndex, cellIndex)) {
            g.setColor(mapFormatter.getCellObjectColor(chunkIndex, cellIndex));
            if (mapFormatter.isCellObjectCircular(chunkIndex, cellIndex)) {
                g.fillOval(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
            } else {
                g.fillRect(x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
            }
        }
        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void drawPlayer(final Graphics g, final int cellWidth, final int cellHeight) {
        if (gameController == null) {
            return;
        }

        // Ottieni la posizione del player dal controller
        final Cell playerPosition = gameController.getPlayerController().getPlayerPosition();
        final Player player = gameController.getPlayerController().getPlayer();

        if (playerPosition == null || player == null || !gameController.getPlayerController().isPlayerAlive()) {
            return;
        }

        // Calcola le coordinate del player sullo schermo
        final int playerCol = playerPosition.getX();
        final int playerChunkY = playerPosition.getY();

        // Trova la riga visibile corrispondente alla posizione Y del player
        final List<Chunk> visibleChunks = gameController.getGameMap().getVisibleChunks();
        int screenRow = -1;

        for (int i = 0; i < visibleChunks.size(); i++) {
            if (visibleChunks.get(i).getPosition() == playerChunkY) {
                screenRow = i;
                break;
            }
        }

        // Se il player non è visibile, non disegnarlo
        if (screenRow == -1 || playerCol < 0 || playerCol >= cellsPerRow) {
            return;
        }

        // Calcola le coordinate pixel
        final int pixelX = playerCol * cellWidth;
        final int pixelY = (chunksNumber - screenRow - 1) * cellHeight + animationOffset;

        // Disegna il player
        drawPlayerSprite(g, pixelX, pixelY, cellWidth, cellHeight, player);
    }

    private void drawPlayerSprite(final Graphics g, final int x, final int y, 
                                final int cellWidth, final int cellHeight, final Player player) {
        Color playerColor = Color.PINK;
        if (player != null && player.getCurrentSkin() != null && player.getCurrentSkin().getColor() != null) {
            playerColor = player.getCurrentSkin().getColor();
        }
        g.setColor(playerColor);
        final int bodyWidth = cellWidth * 3 / 4;
        final int bodyHeight = cellHeight * 3 / 4;
        final int bodyX = x + (cellWidth - bodyWidth) / 2;
        final int bodyY = y + (cellHeight - bodyHeight) / 2;
        g.fillOval(bodyX, bodyY, bodyWidth, bodyHeight);
        g.setColor(Color.BLACK);
        g.drawOval(bodyX, bodyY, bodyWidth, bodyHeight);
    }

    /**
     * Refreshes the panel to reflect any changes in the game state.
     */
    public void refresh() {
        repaint();
    }

    /**
     * Sets the animation offset for the panel.
     * This is used to create a scrolling effect for the game map.
     * @param offset the new animation offset
     */
    public void setAnimationOffset(final int offset) {
        this.animationOffset = offset;
    }

    /**
     * Gets the width of a cell in pixels.
     * @return the width of a cell in pixels
     */
    public int getCellHeight() {
        return getHeight() / chunksNumber;
    }

}
