package it.unibo.view.Shop.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import it.unibo.model.Shop.api.Skin;
import it.unibo.view.ScaleManager;
import it.unibo.view.Shop.api.ShopView;
import it.unibo.view.Shop.api.SkinManager;

public class ShopViewImpl extends JPanel implements ShopView  {

    private final SkinManager skinManager;
    private final ScaleManager scaleManager;
    private int coins;
    private Runnable onBackToMainMenu;

    public ShopViewImpl(SkinManager skinManager, ScaleManager scaleManager) {
        this.skinManager = skinManager;
        this.scaleManager = scaleManager;
        this.coins = 0;

        setPreferredSize(new Dimension(scaleManager.getCurrentWidth(), scaleManager.getCurrentHeight()));
        setBackground(Color.BLACK);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }
    

    @Override
    public void setCoins(int coins) {
       this.coins = coins;
    }

    @Override
    public void setOnBackToMainMenu(Runnable onBackToMainMenu) {
        this.onBackToMainMenu = onBackToMainMenu;}

    @Override
    public void handleMouseClick(int x, int y) {
        int unscaledX = scaleManager.unscaleX(x);
        int unscaledY = scaleManager.unscaleY(y);

        // Check if the "Back" button was clicked
        if (unscaledY >= 500 && unscaledY <= 550) {
            if (onBackToMainMenu != null) {
                onBackToMainMenu.run();
            }
            return;
        }

        List<Skin> skins = skinManager.getAvailableSkins();     
        int skinWidth = scaleManager.scaleX(200);
        int spacing = scaleManager.scaleX(20);
        int totalWidth = skins.size() * (skinWidth + spacing) - spacing;
        int startX = (800 - totalWidth) / 2;

        for (int i = 0; i < skins.size(); i++) {
            Skin skin = skins.get(i);
            int skinX = startX + i * (skinWidth + spacing);

            if (unscaledX >= skinX && unscaledX <= skinX + skinWidth && 
                unscaledY >= 200 && unscaledY <= 350) {
                
                    if ( skin.isUnlocked() ) {
                        skinManager.selectSkin(skin.getId());
                    } else if (coins >= skin.getPrice()) {
                        //notify the controller that the skin is unlocked
                        if (skinManager.buySkin(skin.getId(), coins)) {
                            // Update the coins after purchase
                            coins -= skin.getPrice();
                            setCoins(coins);
                        }
                    }
                    repaint();
                    break;
                }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scaleManager.getScaleX(), scaleManager.getScaleY());
        
        //Title
        g.setColor(Color.WHITE);
        Font titleFont = new Font("Arial", Font.BOLD, 50);
        g.setFont(titleFont);
        g.drawString("Skin Shop", scaleManager.scaleX(300), scaleManager.scaleY(80));

        //Coins
        Font coinsFont = new Font("Arial", Font.BOLD, 30);
        g.setFont(coinsFont);
        g.drawString("Coins: " + coins, scaleManager.scaleX(50), scaleManager.scaleY(50));

        //Draws available skins
        
        //Back button
        g.setColor(new Color(50,40,150));
        g.fillRect(scaleManager.scaleX(50), scaleManager.scaleY(500), scaleManager.scaleX(200), scaleManager.scaleY(50));
        g.setColor(Color.WHITE);
        g.drawString("Back", 380, 530);
    }
   
}
