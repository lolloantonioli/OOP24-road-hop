package it.unibo.view;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
    
    private static final String PLAY_TEXT = "Play";
    private static final String INSTRUCTIONS_TEXT = "Instructions";
    private static final String SHOP_TEXT = "Shop";
    private static final int BETWEEN_LOGO = 20;
    private static final int BETWEEN_BUTTONS = 10;
    private static final int SCALE = 4;

    public static final String SEP = File.separator;
    public static final String LOGO_PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "logo.png";
    
    private int logoScale = SCALE;
    private static final int LOGO_MIN_SCALE = 3;
    private static final int LOGO_MAX_SCALE = 8;

    public MenuPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLUE);
        playButton = new JButton(PLAY_TEXT);
        instructionsButton = new JButton(INSTRUCTIONS_TEXT);
        shopButton = new JButton(SHOP_TEXT);
        logoLabel = new JLabel();
        // Imposta l'icona iniziale in base alla dimensione iniziale del pannello
        updateLogoIcon(getWidth() > 0 ? getWidth() : 800, getHeight() > 0 ? getHeight() : 600);
        
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLUE);
        
        logoLabel.setAlignmentX(CENTER_ALIGNMENT);
        playButton.setAlignmentX(CENTER_ALIGNMENT);
        instructionsButton.setAlignmentX(CENTER_ALIGNMENT);
        shopButton.setAlignmentX(CENTER_ALIGNMENT);
        
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_LOGO)));
        centerPanel.add(playButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_BUTTONS)));
        centerPanel.add(instructionsButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, BETWEEN_BUTTONS)));
        centerPanel.add(shopButton);
        
        this.add(centerPanel);
        
        logoLabel.addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0 && logoScale > LOGO_MIN_SCALE) {
                logoScale--;
            } else if (e.getWheelRotation() > 0 && logoScale < LOGO_MAX_SCALE) {
                logoScale++;
            }
            updateLogoIcon();
        });
    }

    private static ImageIcon loadScaledIcon(final String path, final int scaleDiv) {
        checkNotNull(path, "Path cannot be null");
        checkArgument(scaleDiv > 0, "Scale divisor must be positive");
        final ImageIcon originalIcon = new ImageIcon(path);
        checkArgument(originalIcon.getIconWidth() > 0 && originalIcon.getIconHeight() > 0,
                "Image not found or invalid: %s", path);
        final int iconWidth = originalIcon.getIconWidth() / scaleDiv;
        final int iconHeight = originalIcon.getIconHeight() / scaleDiv;
        final Image scaledImage = originalIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void updateLogoIcon(int panelWidth, int panelHeight) {
        // Calcola la dimensione in base alla dimensione del pannello
        int minDim = Math.min(panelWidth, panelHeight);
        int scaleDiv = Math.max(1, 10 - minDim / 150); // Più grande -> immagine più grande
        final ImageIcon logoIcon = loadScaledIcon(LOGO_PATH, scaleDiv);
        logoLabel.setIcon(logoIcon);
    }

    private void updateLogoIcon() {
        final ImageIcon logoIcon = loadScaledIcon(LOGO_PATH, logoScale);
        logoLabel.setIcon(logoIcon);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateLogoIcon(width, height);
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