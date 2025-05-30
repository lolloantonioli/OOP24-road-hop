package it.unibo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import it.unibo.model.Shop.api.Skin;

public class ShopView extends JPanel {

    private JLabel titleLabel;
    private JLabel coinsLabel; 
    private JButton backButton;
    private final JPanel skinsPanel;
    private final JScrollPane scrollPane;

    private List<Skin> skins = new ArrayList<>();
    private int coins = 0;

    private Runnable onBackToMainMenu;
    private BiConsumer<String, Integer> onSkinPurchase;
    private Consumer<String> onSkinSelected;


    public ShopView() {
        setLayout(new BorderLayout());
        setBackground(Color.BLUE);

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        skinsPanel = new JPanel();
        skinsPanel.setLayout(new GridLayout(0, 3));
        skinsPanel.setBackground(Color.BLUE);

        scrollPane = new JScrollPane(skinsPanel);
        scrollPane.setBackground(Color.BLUE);
        scrollPane.getViewport().setBackground(Color.BLUE);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
           
    }
    

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel("Skin Shop",SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC, titleLabel.getFont().getSize() * 1.5f));

        coinsLabel = new JLabel("Coins: 0", SwingConstants.RIGHT);
        coinsLabel.setForeground(Color.YELLOW);
        coinsLabel.setFont(titleLabel.getFont().deriveFont(Font.ITALIC, titleLabel.getFont().getSize() * 1.5f));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(coinsLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.BLUE);

        backButton = new JButton("Back");
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());

        backButton.setFont(backButton.getFont().deriveFont(Font.ROMAN_BASELINE));
        
        backButton.addActionListener(e -> {
            if (onBackToMainMenu != null) {
                onBackToMainMenu.run();
            }
        });

        footerPanel.add(backButton);

        return footerPanel;
    }

    private void refreshSkins() {
        skinsPanel.removeAll();
        for (Skin skin : skins) {
            skinsPanel.add(createSkinCard(skin));
        }
        skinsPanel.revalidate();
        skinsPanel.repaint();
    }


private Component createSkinCard(Skin skin) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(171, 205, 239));
        
        // Different border colors based on skin status
        Color borderColor;
        if (skin.isSelected()) {
            borderColor = Color.CYAN; // Selected skin
        } else if (skin.isUnlocked()) {
            borderColor = Color.GREEN; // Owned but not selected
        } else {
            borderColor = Color.GRAY; // Not owned
        }
        
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Skin image (using different frog emojis for variety)
        String frogEmoji = getFrogEmojiForSkin(skin.getId());
        JLabel imageLabel = new JLabel(frogEmoji, SwingConstants.CENTER);
        // Use relative font size based on default font
        imageLabel.setFont(imageLabel.getFont().deriveFont(imageLabel.getFont().getSize() * 4.0f));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Skin name
        JLabel nameLabel = new JLabel(skin.getName(), SwingConstants.CENTER);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, nameLabel.getFont().getSize() * 1.2f));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Status/Price label
        JLabel statusLabel;
        if (skin.isSelected()) {
            statusLabel = new JLabel("✓ EQUIPPED", SwingConstants.CENTER);
            statusLabel.setForeground(Color.CYAN);
            statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        } else if (skin.isUnlocked()) {
            statusLabel = new JLabel("OWNED", SwingConstants.CENTER);
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        } else {
            statusLabel = new JLabel("💰 " + skin.getPrice() + " coins", SwingConstants.CENTER);
            statusLabel.setForeground(Color.YELLOW);
            statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
        }
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action button
        JButton actionButton;
        if (skin.isSelected()) {
            actionButton = new JButton("EQUIPPED");
            actionButton.setBackground(new Color(0, 150, 150));
            actionButton.setEnabled(false);
        } else if (skin.isUnlocked()) {
            actionButton = new JButton("EQUIP");
            actionButton.setBackground(new Color(34, 139, 34));
            actionButton.addActionListener(e -> {
                if (onSkinSelected != null) {
                    onSkinSelected.accept(skin.getId());
                }
            });
        } else {
            actionButton = new JButton("BUY");
            boolean canAfford = coins >= skin.getPrice();
            actionButton.setBackground(canAfford ? new Color(255, 140, 0) : new Color(100, 100, 100));
            actionButton.setEnabled(canAfford);
            actionButton.addActionListener(e -> {
                if (onSkinPurchase != null && canAfford) {
                    onSkinPurchase.accept(skin.getId(), skin.getPrice());
                }
            });
        }
        
        actionButton.setForeground(Color.WHITE);
        actionButton.setFocusPainted(false);
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionButton.setFont(actionButton.getFont().deriveFont(Font.BOLD));
        actionButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Add components to card with flexible spacing
        card.add(Box.createVerticalGlue());
        card.add(imageLabel);
        card.add(Box.createVerticalStrut(5)); // Small fixed strut for minimum spacing
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(statusLabel);
        card.add(Box.createVerticalGlue());
        card.add(actionButton);
        card.add(Box.createVerticalStrut(5));
        
        return card;
    }

    private String getFrogEmojiForSkin(String skinId) {
        switch (skinId.toLowerCase()) {
            case "default": return "🐸";
            case "red": return "🔴🐸";
            case "blue": return "🔵🐸";
            case "gold": return "🟡🐸";
            case "rainbow": return "🌈🐸";
            default: return "🐸";
        }
    }
    
    public void setCoins(int coins) {
       this.coins = coins;
       coinsLabel.setText("Coins: " + coins);
       refreshSkins();
    }    

    
    public void setOnBackToMainMenu(Runnable onBackToMainMenu) {
        this.onBackToMainMenu = onBackToMainMenu;
    }

    
    public void setSkins(List<Skin> skins) {
        this.skins = new ArrayList<>(skins);
        refreshSkins();
    }


    
    public void setOnSkinPurchase(BiConsumer<String, Integer> skinPurchase) {
        this.onSkinPurchase = skinPurchase;
    }


    
    public void setOnSkinSelected(Consumer<String> onSkinSelected) {
        this.onSkinSelected = onSkinSelected;
    }
   
}
