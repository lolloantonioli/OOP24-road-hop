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

public class GameOverPanel extends JPanel {
    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String MENU_BUTTON_TEXT = "Menu";
    private final JButton menuButton;
    private final JLabel gameOverLabel;

    public GameOverPanel(Runnable onMenu) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLUE);
        gameOverLabel = new JLabel(GAME_OVER_TEXT);
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        menuButton = new JButton(MENU_BUTTON_TEXT);
        menuButton.setFont(new Font("Arial", Font.BOLD, 32));
        menuButton.addActionListener(e -> onMenu.run());
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        gameOverLabel.setAlignmentX(CENTER_ALIGNMENT);
        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(gameOverLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(menuButton);
        add(centerPanel);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int minDim = Math.min(width, height);
        int titleFontSize = Math.max(32, minDim / 6);
        int buttonFontSize = Math.max(12, minDim / 15);
        gameOverLabel.setFont(gameOverLabel.getFont().deriveFont((float) titleFontSize));
        menuButton.setFont(menuButton.getFont().deriveFont((float) buttonFontSize));
        int minButtonWidth = getFontMetrics(menuButton.getFont()).stringWidth(MENU_BUTTON_TEXT) + 40;
        int buttonWidth = Math.max(minButtonWidth, width / 3);
        int buttonHeight = Math.max(40, height / 10);
        menuButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        menuButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        menuButton.setPreferredSize(null);
        menuButton.setHorizontalTextPosition(JButton.CENTER);
        menuButton.setText(MENU_BUTTON_TEXT);
    }
}