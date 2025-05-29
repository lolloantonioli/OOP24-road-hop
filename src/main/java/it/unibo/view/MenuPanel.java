package it.unibo.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private final JButton playButton;
    private final JButton instructionsButton;
    private final JButton shopButton;
    private final JLabel titleLabel;
    
    private static final String PLAY_TEXT = "Play";
    private static final String INSTRUCTIONS_TEXT = "Instructions";
    private static final String SHOP_TEXT = "Shop";
    private static final int BETWEEN_LOGO = 20;
    private static final int BETWEEN_BUTTONS = 10;


    public static final String SEP = File.separator;
    public static final String LOGO_PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "logo.png";
    

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);
        playButton = new JButton(PLAY_TEXT);
        instructionsButton = new JButton(INSTRUCTIONS_TEXT);
        shopButton = new JButton(SHOP_TEXT);
        // Titolo del gioco
        titleLabel = new JLabel("Road Hop");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_LOGO)));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_LOGO)));
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
        shopButton.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(playButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_BUTTONS)));
        centerPanel.add(instructionsButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_BUTTONS)));
        centerPanel.add(shopButton);
        this.add(centerPanel);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int minDim = Math.min(width, height);
        int baseFontSize = Math.max(12, minDim / 15);
        playButton.setFont(playButton.getFont().deriveFont((float) baseFontSize));
        instructionsButton.setFont(instructionsButton.getFont().deriveFont((float) baseFontSize));
        shopButton.setFont(shopButton.getFont().deriveFont((float) baseFontSize));
        // Calcola la larghezza minima necessaria per il testo più lungo
        int minButtonWidth = getFontMetrics(instructionsButton.getFont()).stringWidth(INSTRUCTIONS_TEXT) + 40;
        int buttonWidth = Math.max(minButtonWidth, width / 3);
        int buttonHeight = Math.max(40, height / 10);
        playButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        instructionsButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        shopButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        playButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        instructionsButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        shopButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        playButton.setPreferredSize(null);
        instructionsButton.setPreferredSize(null);
        shopButton.setPreferredSize(null);
        playButton.setHorizontalTextPosition(JButton.CENTER);
        instructionsButton.setHorizontalTextPosition(JButton.CENTER);
        shopButton.setHorizontalTextPosition(JButton.CENTER);
        playButton.setText(PLAY_TEXT);
        instructionsButton.setText(INSTRUCTIONS_TEXT);
        shopButton.setText(SHOP_TEXT);
        // Ridimensiona anche il titolo in modo adattivo
        int titleFontSize = Math.max(32, minDim / 6);
        titleLabel.setFont(titleLabel.getFont().deriveFont((float) titleFontSize));
    }

    public void setPlayAction(final Runnable action) {
        playButton.addActionListener(e -> action.run());
    }

    public void setSettingsAction(final Runnable action) {
        instructionsButton.addActionListener(e -> action.run());
    }

    public void setShopAction(final Runnable action) {
        shopButton.addActionListener(e -> action.run());
    }

}