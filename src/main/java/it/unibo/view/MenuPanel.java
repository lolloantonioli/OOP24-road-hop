package it.unibo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

    private final JButton playButton;
    private final JButton instructionsButton;
    private final JButton shopButton;
    private final JLabel logoLabel;
    
    public static final String SEP = File.separator;
    public static final String LOGO_PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "logo.png";
    
    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.RED);
        
        playButton = new JButton("Play");
        instructionsButton = new JButton("Instructions");
        shopButton = new JButton("Shop");
        logoLabel = new JLabel();
        
        ImageIcon originalIcon = new ImageIcon(LOGO_PATH);
        int logoWidth = originalIcon.getIconWidth() / 4;
        int logoHeight = originalIcon.getIconHeight() / 4;
        Image scaledImage = originalIcon.getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.RED);
        
        logoLabel.setAlignmentX(CENTER_ALIGNMENT);
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
        shopButton.setAlignmentX(CENTER_ALIGNMENT);
        
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(playButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(instructionsButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(shopButton);
        
        this.add(centerPanel);
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