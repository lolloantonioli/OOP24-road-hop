package it.unibo.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanelImpl extends JPanel implements MenuPanel {

    private final JButton playButton;
    private final JButton settingsButton;
    private final JButton shopButton;
    private final BoxLayout layout;
    private final JLabel logoLabel;
    private ImageIcon logoIcon;
    
    public static final String SEP = File.separator;
    public static final String LOGO_PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "logo.png";

    public MenuPanelImpl() {
        layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
        shopButton = new JButton("Shop");
        logoIcon = new ImageIcon(LOGO_PATH);
        logoLabel = new JLabel();

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();

        final int logoWidth = sw / 6;  // Puoi modificare questo rapporto
        final int logoHeight = sh / 6; // Puoi modificare questo rapporto

        ImageIcon originalIcon = new ImageIcon(LOGO_PATH);
        Image scaledImage = originalIcon.getImage().getScaledInstance(
            logoWidth, logoHeight, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);

        logoLabel.setIcon(logoIcon);
        this.setLayout(layout);
        this.add(logoLabel);
        this.add(playButton);
        this.add(settingsButton);
        this.add(shopButton);
    }

}
