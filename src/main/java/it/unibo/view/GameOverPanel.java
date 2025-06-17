package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents a panel displayed when the game is over.
 * It contains a label indicating "Game Over" and a button to return to the menu.
 */
public final class GameOverPanel extends JPanel {


    private static final int LABEL_SIZE = 48;
    private static final int LABEL_BIN_SIZE = 32;
    private static final int HEIGHT_AREA = 40;
    private static final int DIV_FACTOR_TITLE = 6;
    private static final int DIV_FACTOR_BUTTON = 15;
    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String MENU_BUTTON_TEXT = "Menu";

    private final JButton menuButton;
    private final JLabel gameOverLabel;
    private final JLabel coinsLabel;
    private final JLabel scoreLabel;
    private final JLabel maxScoreLabel;

    public GameOverPanel(final Runnable onMenu) {
        this(onMenu, 0, 0, 0);
    }

    /**
     * Constructs a GameOverPanel with a button to return to the menu.
     *
     * @param onMenu the action to perform when the menu button is clicked
     * @param finalScore the final score of the game
     * @param finalCoins the final number of coins collected in the game
     * @param maxScore the maximum score achieved in the game
     */
    public GameOverPanel(final Runnable onMenu, final int finalScore, final int finalCoins, final int maxScore) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLUE);
        gameOverLabel = new JLabel(GAME_OVER_TEXT);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
        menuButton = new JButton(MENU_BUTTON_TEXT);
        menuButton.setFont(new Font("Arial", Font.BOLD, LABEL_BIN_SIZE));
        menuButton.addActionListener(e -> onMenu.run());
        scoreLabel = new JLabel("Score: " + finalScore);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, LABEL_BIN_SIZE));
        coinsLabel = new JLabel("Coins: " + finalCoins);
        coinsLabel.setForeground(Color.WHITE);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, LABEL_BIN_SIZE));
        maxScoreLabel = new JLabel("Max Score: " + maxScore);
        maxScoreLabel.setForeground(Color.YELLOW);
        maxScoreLabel.setFont(new Font("Arial", Font.BOLD, LABEL_BIN_SIZE));
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(gameOverLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT_AREA)));
        centerPanel.add(menuButton);
        centerPanel.add(scoreLabel);
        centerPanel.add(coinsLabel);
        centerPanel.add(maxScoreLabel);
        add(centerPanel);
    }

    /**
     * Sets the bounds of the panel and adjusts the font sizes based on the panel's dimensions.
     *
     * @param x the x coordinate of the panel
     * @param y the y coordinate of the panel
     * @param width the width of the panel
     * @param height the height of the panel
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);
        final int minDim = Math.min(width, height);
        final int titleFontSize = Math.max(32, minDim / DIV_FACTOR_TITLE);
        final int buttonFontSize = Math.max(12, minDim / DIV_FACTOR_BUTTON);
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont((float) titleFontSize));
        menuButton.setFont(menuButton.getFont().deriveFont((float) buttonFontSize));
        final int minButtonWidth = getFontMetrics(menuButton.getFont()).stringWidth(MENU_BUTTON_TEXT) + 40;
        final int buttonWidth = Math.max(minButtonWidth, width / 3);
        final int buttonHeight = Math.max(40, height / 10);
        menuButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        menuButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        menuButton.setPreferredSize(null);
        menuButton.setHorizontalTextPosition(JButton.CENTER);
        menuButton.setText(MENU_BUTTON_TEXT);
    }

    /**
     * Sets the final number of coins collected in the game.
     *
     * @param finalCoins the final number of coins
     */
    public void setFinalCoins(final int finalCoins) {
        coinsLabel.setText("Coins: " + finalCoins);
    }

    /**
     * Sets the final score of the game.
     *
     * @param finalScore the final score
     */
    public void setFinalScore(final int finalScore) {
        scoreLabel.setText("Score: " + finalScore);
    }

    /**
     * Sets the maximum score achieved in the game.
     *
     * @param maxScore the maximum score
     */
    public void setMaxScore(final int maxScore) {
        maxScoreLabel.setText("Max Score: " + maxScore);
    }
}
