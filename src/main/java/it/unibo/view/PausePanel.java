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

}
