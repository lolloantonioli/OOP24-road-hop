package it.unibo.view.Menu.impl;

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

import it.unibo.view.Menu.api.MenuPanel;

public class MenuPanelImpl extends JPanel implements MenuPanel {

    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton shopButton;
    private final JLabel logoLabel;
    
    public static final String SEP = File.separator;
    public static final String LOGO_PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "logo.png";
    
    public MenuPanelImpl() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.RED);
        
        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
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
        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        shopButton.setAlignmentX(CENTER_ALIGNMENT);
        
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(playButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(settingsButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(shopButton);
        
        this.add(centerPanel);
    }

}