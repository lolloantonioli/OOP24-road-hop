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

import it.unibo.controller.MainController;

/**
 * MenuPanel class that represents the main menu of the game.
 * It contains buttons for playing the game, viewing instructions, and accessing the shop.
 * The panel is designed to be responsive and adjusts its layout based on the size of the window.
 */
public final class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String PLAY_TEXT = "Play";
    private static final String INSTRUCTIONS_TEXT = "Instructions";
    private static final String SHOP_TEXT = "Shop";
    private static final int BETWEEN_LOGO = 20;
    private static final int BETWEEN_BUTTONS = 10;
    private static final int FONT_SIZE = 48;

    private final JButton playButton;
    private final JButton instructionsButton;
    private final JButton shopButton;
    private final JLabel titleLabel;
    private final transient MainController controller;

    /**
     * Constructor for the MenuPanel.
     * Initializes the layout, buttons, and title label.
     *
     * @param controller the MainController to handle actions when buttons are clicked
     */
    public MenuPanel(final MainController controller) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);
        this.controller = controller;
        playButton = new JButton(PLAY_TEXT);
        this.setPlayAction();
        instructionsButton = new JButton(INSTRUCTIONS_TEXT);
        this.setInstructionsAction();
        shopButton = new JButton(SHOP_TEXT);
        this.setShopAction();
        titleLabel = new JLabel("Road Hop");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
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

    /**
     * Sets the bounds of the panel and adjusts the size of the buttons and title label based on the panel's dimensions.
     *
     * @param x      the x-coordinate of the panel
     * @param y      the y-coordinate of the panel
     * @param width  the width of the panel
     * @param height the height of the panel
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);
        final int minDim = Math.min(width, height);
        final int baseFontSize = Math.max(12, minDim / 15);
        playButton.setFont(playButton.getFont().deriveFont((float) baseFontSize));
        instructionsButton.setFont(instructionsButton.getFont().deriveFont((float) baseFontSize));
        shopButton.setFont(shopButton.getFont().deriveFont((float) baseFontSize));
        final int minButtonWidth = getFontMetrics(instructionsButton.getFont()).stringWidth(INSTRUCTIONS_TEXT) + 40;
        final int buttonWidth = Math.max(minButtonWidth, width / 3);
        final int buttonHeight = Math.max(40, height / 10);
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
        final int titleFontSize = Math.max(32, minDim / 6);
        titleLabel.setFont(titleLabel.getFont().deriveFont((float) titleFontSize));
    }

    /**
     * Sets the action for the play button to navigate to the game.
     */
    public void setPlayAction() {
        playButton.addActionListener(e -> controller.goToGame());
    }

    /**
     * Sets the action for the instructions button to navigate to the instructions screen.
     */
    public void setInstructionsAction() {
        instructionsButton.addActionListener(e -> controller.goToInstructions());
    }

    /**
     * Sets the action for the shop button to navigate to the shop.
     */
    public void setShopAction() {
        shopButton.addActionListener(e -> controller.goToShop());
    }

}
