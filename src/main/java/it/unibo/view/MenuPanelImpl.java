package it.unibo.view;

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
    
    public static final String separator = System.getProperty("file.separator");
    public static final String LOGO_PATH = "src" + separator + "main" + separator + "resources" + separator + "logo.png";

    public MenuPanelImpl() {
        layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
        playButton = new JButton("Play");
        settingsButton = new JButton("Settings");
        shopButton = new JButton("Shop");
        ImageIcon logoIcon = new ImageIcon(LOGO_PATH);
        logoLabel = new JLabel(logoIcon);
        this.setLayout(layout);
        this.add(logoLabel);
        this.add(playButton);
        this.add(settingsButton);
        this.add(shopButton);
    }

}
