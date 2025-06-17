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

    private final int labelSize = 48;
    private final int labelBinSize = 32;
    private final int heightArea = 40;
    private final int divFactorTitle = 6;
    private final int divFactonButton = 15;


    private final String gameOverText = "Game Over";
    private final String menuButtonText = "Menu";
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
        gameOverLabel = new JLabel(gameOverText);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, labelSize));
        menuButton = new JButton(menuButtonText);
        menuButton.setFont(new Font("Arial", Font.BOLD, labelBinSize));
        menuButton.addActionListener(e -> onMenu.run());
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(gameOverLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, heightArea)));
        centerPanel.add(menuButton);
        add(centerPanel);
    }

    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);
        final int minDim = Math.min(width, height);
        final int titleFontSize = Math.max(32, minDim / divFactorTitle);
        final int buttonFontSize = Math.max(12, minDim / divFactonButton);
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont((float) titleFontSize));
        menuButton.setFont(menuButton.getFont().deriveFont((float) buttonFontSize));
        final int minButtonWidth = getFontMetrics(menuButton.getFont()).stringWidth(menuButtonText) + 40;
        final int buttonWidth = Math.max(minButtonWidth, width / 3);
        final int buttonHeight = Math.max(40, height / 10);
        menuButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        menuButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        menuButton.setPreferredSize(null);
        menuButton.setHorizontalTextPosition(JButton.CENTER);
        menuButton.setText(menuButtonText);
    }
}
