package it.unibo.view.Shop.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;

import it.unibo.view.ScaleManager;
import it.unibo.view.Shop.api.ShopView;

public class ShopViewImpl extends JPanel implements ShopView  {

    private final ScaleManager skinManager;
    private final ScaleManager scaleManager;
    private int coins;
    private Runnable onBackToMainMenu;

    public ShopViewImpl(ScaleManager skinManager, ScaleManager scaleManager) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleMouseClick'");
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
