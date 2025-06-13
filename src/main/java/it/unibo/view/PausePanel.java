package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PausePanel extends JPanel {

    private final JButton continueButton;;
    private final JButton menuButton;

    private static final String MENU_BUTTON_TEXT = "Menu";
    private static final String CONTINUE_BUTTON_TEXT = "Continue";
    
    public PausePanel(Runnable onContinue, Runnable onMenu) {
        menuButton = new JButton(MENU_BUTTON_TEXT);
        continueButton = new JButton(CONTINUE_BUTTON_TEXT);
        this.setMenuAction(onMenu);
        this.setContinueAction(onContinue);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);

        menuButton.setAlignmentX(CENTER_ALIGNMENT);
        continueButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(menuButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        centerPanel.add(continueButton);
        
        this.add(centerPanel);
    }

    private void setContinueAction(Runnable onContinue) {
        continueButton.addActionListener(e -> onContinue.run());
    }

    private void setMenuAction(Runnable onMenu) {
        menuButton.addActionListener(e -> onMenu.run());
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int minDim = Math.min(width, height);
        int baseFontSize = Math.max(12, minDim / 15);
        // Aggiorna il font dei bottoni
        continueButton.setFont(continueButton.getFont().deriveFont((float) baseFontSize));
        menuButton.setFont(menuButton.getFont().deriveFont((float) baseFontSize));
        // Calcola larghezza minima necessaria
        int minButtonWidth = Math.max(
            getFontMetrics(continueButton.getFont()).stringWidth(CONTINUE_BUTTON_TEXT),
            getFontMetrics(menuButton.getFont()).stringWidth(MENU_BUTTON_TEXT)
        ) + 40;
        int buttonWidth = Math.max(minButtonWidth, width / 3);
        int buttonHeight = Math.max(40, height / 10);
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
