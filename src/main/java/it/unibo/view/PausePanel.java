package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * PausePanel class that represents the pause menu of the game.
 * It contains buttons to continue the game or return to the main menu.
 * The panel is designed to be responsive and adjusts its layout based on the size of the window.
 */
public final class PausePanel extends JPanel {

    private final String MENU_BUTTON_TEXT = "Menu";
    private final String CONTINUE_BUTTON_TEXT = "Continue";

    private final JButton continueButton;;
    private final JButton menuButton;

    /**
     * Constructor for the PausePanel.
     * @param onContinue the action to perform when the continue button is clicked
     * @param onMenu the action to perform when the menu button is clicked
     */
    public PausePanel(final Runnable onContinue, final Runnable onMenu) {
        menuButton = new JButton(MENU_BUTTON_TEXT);
        continueButton = new JButton(CONTINUE_BUTTON_TEXT);
        this.setMenuAction(onMenu);
        this.setContinueAction(onContinue);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);

        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        continueButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(menuButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        centerPanel.add(continueButton);

        this.add(centerPanel);
    }

    private void setContinueAction(final Runnable onContinue) {
        continueButton.addActionListener(e -> onContinue.run());
    }

    private void setMenuAction(final Runnable onMenu) {
        menuButton.addActionListener(e -> onMenu.run());
    }

    /**
     * Sets the bounds of the panel and adjusts the size of the buttons based on the panel's dimensions.
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);
        final int minDim = Math.min(width, height);
        final int baseFontSize = Math.max(12, minDim / 15);
        continueButton.setFont(continueButton.getFont().deriveFont((float) baseFontSize));
        menuButton.setFont(menuButton.getFont().deriveFont((float) baseFontSize));
        final int minButtonWidth = Math.max(
            getFontMetrics(continueButton.getFont()).stringWidth(CONTINUE_BUTTON_TEXT),
            getFontMetrics(menuButton.getFont()).stringWidth(MENU_BUTTON_TEXT)
        ) + 40;
        final int buttonWidth = Math.max(minButtonWidth, width / 3);
        final int buttonHeight = Math.max(40, height / 10);
        continueButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        menuButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        continueButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        menuButton.setMinimumSize(new Dimension(minButtonWidth, buttonHeight));
        continueButton.setPreferredSize(null);
        menuButton.setPreferredSize(null);
        continueButton.setHorizontalTextPosition(JButton.CENTER);
        menuButton.setHorizontalTextPosition(JButton.CENTER);
        continueButton.setText(CONTINUE_BUTTON_TEXT);
        menuButton.setText(MENU_BUTTON_TEXT);
    }
}
