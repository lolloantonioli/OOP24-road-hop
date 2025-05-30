package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.controller.MainController;

public class InstructionsPanel extends JPanel {

    private final JTextArea instructions;
    private final JButton backButton;
    private final MainController controller;

    private static final int HEIGHT = 20;
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String INSTRUCTIONS_TEXT = "Instructions:\n"
            + "1. Use WASD to move your character.\n"
            + "2. Collect coins.\n"
            + "3. Avoid obstacles to stay alive.\n"
            + "4. Reach the maximum score.";
    
    public InstructionsPanel(final MainController controller) {
        this.controller = controller;
        instructions = new JTextArea(INSTRUCTIONS_TEXT);
        backButton = new JButton(BACK_BUTTON_TEXT);
        this.setBackAction();

        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);

        instructions.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(instructions);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        centerPanel.add(backButton);
        
        this.add(centerPanel);
    }

    public void setBackAction() {
        backButton.addActionListener(e -> controller.goToMenu());
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int minDim = Math.min(width, height);
        int baseFontSize = Math.max(12, minDim / 18);
        // Calcola la larghezza massima per il testo
        FontMetrics fm = instructions.getFontMetrics(new Font("Arial", Font.PLAIN, baseFontSize));
        int textWidth = fm.stringWidth(instructions.getText());
        int lines = instructions.getLineCount();
        int maxWidth = Math.max(width - 40, 100);
        int maxHeight = Math.max(height - 80, 60);
        // Adatta il font per riempire sia in larghezza che in altezza
        while ((fm.getHeight() * lines < maxHeight) && (textWidth < maxWidth)) {
            baseFontSize++;
            fm = instructions.getFontMetrics(new Font("Arial", Font.PLAIN, baseFontSize));
            textWidth = fm.stringWidth(instructions.getText());
        }
        baseFontSize = Math.max(12, baseFontSize - 1);
        instructions.setFont(new Font("Arial", Font.PLAIN, baseFontSize));
        instructions.setMaximumSize(new Dimension(maxWidth, maxHeight));
        instructions.setMinimumSize(new Dimension(60, 40));
        instructions.setPreferredSize(new Dimension(maxWidth, maxHeight));
        backButton.setFont(backButton.getFont().deriveFont((float) baseFontSize));
        int buttonWidth = Math.max(120, width / 3);
        int buttonHeight = Math.max(40, height / 10);
        backButton.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        backButton.setMinimumSize(new Dimension(0, buttonHeight));
        backButton.setPreferredSize(null);
    }

}
