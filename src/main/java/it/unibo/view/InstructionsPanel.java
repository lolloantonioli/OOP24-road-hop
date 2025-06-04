package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

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
        instructions.setBackground(Color.BLUE);
        instructions.setForeground(Color.WHITE);

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
    
        // Imposta il layout per occupare tutto lo spazio disponibile
        setLayout(new BorderLayout());
    
        // Aggiorna il font e le dimensioni del JTextArea
        int fontSize = Math.max(16, Math.min(width / 35, height / 18));
        instructions.setFont(new Font("Arial", Font.PLAIN, fontSize));
        instructions.setPreferredSize(new Dimension(width, height - 100));
    
        // Aggiorna il font e le dimensioni del JButton
        backButton.setFont(backButton.getFont().deriveFont((float) fontSize));
        backButton.setPreferredSize(new Dimension(width / 3, 50));
    
        // Aggiungi i componenti al layout
        removeAll(); // Rimuovi eventuali componenti precedenti
        add(instructions, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    
        // Forza il ridimensionamento e il ridisegno dei componenti
        revalidate();
        repaint();
    }
}
