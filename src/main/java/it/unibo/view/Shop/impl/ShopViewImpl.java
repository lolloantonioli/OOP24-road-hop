package it.unibo.view.Shop.impl;

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

import it.unibo.view.Shop.api.ShopView;
public class ShopViewImpl extends JPanel implements ShopView  {

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


    public ShopViewImpl() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        skinsPanel = new JPanel();
        skinsPanel.setLayout(new GridLayout(0, 3));
        skinsPanel.setBackground(Color.BLACK);

        scrollPane = new JScrollPane(skinsPanel);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
           
    }
    

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);

        titleLabel = new JLabel("Skin Shop",SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        coinsLabel = new JLabel("Coins: 0", SwingConstants.RIGHT);
        coinsLabel.setForeground(Color.YELLOW);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 20));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(coinsLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.BLACK);

        backButton = new JButton("Back");
        backButton.setBackground(new Color(70,130, 200));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());

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
        card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(skin.isUnlocked() ? Color.GREEN : Color.GRAY, 2));
        card.setPreferredSize(null);
        
        JLabel imageLabel = new JLabel("🐸", SwingConstants.CENTER); // Placeholder emoji
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(skin.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Price/Status
        JLabel statusLabel;
        if (skin.isUnlocked()) {
            statusLabel = new JLabel("OWNED", SwingConstants.CENTER);
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel = new JLabel(skin.getPrice() + " coins", SwingConstants.CENTER);
            statusLabel.setForeground(Color.YELLOW);
        }
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action button
        JButton actionButton;
        if (skin.isUnlocked()) {
            actionButton = new JButton("Select");
            actionButton.setBackground(new Color(34, 139, 34));
            actionButton.addActionListener(e -> {
                if (onSkinSelected != null) {
                    onSkinSelected.accept(skin.getId());
                    refreshSkins(); // Refresh to show selected state
                }
            });
        } else {
            actionButton = new JButton("Buy");
            actionButton.setBackground(coins >= skin.getPrice() ? new Color(255, 140, 0) : Color.GRAY);
            actionButton.setEnabled(coins >= skin.getPrice());
            actionButton.addActionListener(e -> {
                if (onSkinPurchase != null && coins >= skin.getPrice()) {
                    onSkinPurchase.accept(skin.getId(), skin.getPrice());
                    refreshSkins(); // Refresh after purchase
                }
            });
        }
        
        actionButton.setForeground(Color.WHITE);
        actionButton.setFocusPainted(false);
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalGlue());
        card.add(imageLabel);
        card.add(Box.createVerticalGlue());
        card.add(nameLabel);
        card.add(Box.createVerticalGlue());
        card.add(statusLabel);
        card.add(Box.createVerticalGlue());
        card.add(actionButton);
        card.add(Box.createVerticalGlue());
        
        return card;
    }

    
    @Override
    public void setCoins(int coins) {
       this.coins = coins;
       coinsLabel.setText("Coins: " + coins);
       refreshSkins();
    }    

    @Override
    public void setOnBackToMainMenu(Runnable onBackToMainMenu) {
        this.onBackToMainMenu = onBackToMainMenu;
    }


    @Override
    public void setSkins(List<Skin> skins) {
        this.skins = new ArrayList<>(skins);
        refreshSkins();
    }


    @Override
    public void setOnSkinPurchase(BiConsumer<String, Integer> skinPurchase) {
        this.onSkinPurchase = skinPurchase;
    }


    @Override
    public void setOnSkinSelected(Consumer<String> onSkinSelected) {
        this.onSkinSelected = onSkinSelected;
    }
   
}
