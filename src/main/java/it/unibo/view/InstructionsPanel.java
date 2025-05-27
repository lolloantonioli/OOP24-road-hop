package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InstructionsPanel extends JPanel {

    private final JTextArea instructions;
    private final JButton backButton;

    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String INSTRUCTIONS_TEXT = "Instructions:\n"
            + "1. Use WASD to move your character.\n"
            + "2. Collect coins.\n"
            + "3. Avoid obstacles to stay alive.\n"
            + "4. Reach the maximum score.";
    
    public InstructionsPanel() {
        instructions = new JTextArea(INSTRUCTIONS_TEXT);
        backButton = new JButton(BACK_BUTTON_TEXT);

        instructions.setBounds(50, 50, 300, 200);
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.RED);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.RED);

        instructions.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(instructions);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(backButton);
        
        this.add(centerPanel);
    }

    public void setBackAction(final Runnable action) {
        backButton.addActionListener(e -> action.run());
    }

}
