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
    private static final int LABEL_BTN_SIZE = 32;
    private static final int HEIGHT_AREA = 40;
    private static final int DIV_FACTOR_TITLE = 6;
    private static final int DIV_FACTOR_BUTTON = 15;

    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String MENU_BUTTON_TEXT = "Menu";
    private final JButton menuButton;
    private final JLabel gameOverLabel;

    /**
     * Constructs a GameOverPanel with a button to return to the menu.
     *
     * @param onMenu the action to perform when the menu button is clicked
     */
    public GameOverPanel(final Runnable onMenu) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLUE);
        gameOverLabel = new JLabel(GAME_OVER_TEXT);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
        menuButton = new JButton(MENU_BUTTON_TEXT);
        menuButton.setFont(new Font("Arial", Font.BOLD, LABEL_BTN_SIZE));
        menuButton.addActionListener(e -> onMenu.run());
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(gameOverLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT_AREA)));
        centerPanel.add(menuButton);
        add(centerPanel);
    }

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
}
