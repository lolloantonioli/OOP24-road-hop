package it.unibo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import it.unibo.controller.MainController;

/**
 * InstructionsPanel class that displays the game instructions and a back button.
 * It allows users to read the instructions and return to the main menu.
 */
public final class InstructionsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 20;
    private static final String BACK_BUTTON_TEXT = "Back";
    private static final String INSTRUCTIONS_TEXT = "Instructions:\n"
            + "1. Use WASD to move your character.\n"
            + "2. Collect coins.\n"
            + "3. Avoid obstacles to stay alive.\n"
            + "4. Reach the maximum score.";

    private final JTextArea instructions;
    private final JButton backButton;
    private transient final MainController controller;

    /**
     * Constructor for InstructionsPanel.
     * @param controller the MainController to handle navigation actions
     */
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

        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);

        instructions.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setAlignmentX(CENTER_ALIGNMENT);

        centerPanel.add(instructions);
        centerPanel.add(Box.createRigidArea(new Dimension(0, HEIGHT)));
        centerPanel.add(backButton);

        this.add(centerPanel);
    }

    private void setBackAction() {
        backButton.addActionListener(e -> controller.goToMenu());
    }

    /**
     * Sets the bounds of the panel and adjusts the layout and components accordingly.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width of the panel
     * @param height the height of the panel
     */
    @Override
    public void setBounds(final int x, final int y, final int width, final int height) {
        super.setBounds(x, y, width, height);

        setLayout(new BorderLayout());

        final int fontSize = Math.max(16, Math.min(width / 35, height / 18));
        instructions.setFont(new Font("Arial", Font.PLAIN, fontSize));
        instructions.setPreferredSize(new Dimension(width, height - 100));

        backButton.setFont(backButton.getFont().deriveFont((float) fontSize));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);

        removeAll();
        add(instructions, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }
}
