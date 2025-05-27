package it.unibo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import it.unibo.controller.Util.CardName;
import it.unibo.view.Menu.api.MenuPanel;
import it.unibo.view.Menu.impl.MenuPanelImpl;
import it.unibo.view.Shop.api.ShopView;
import it.unibo.view.Shop.impl.ShopViewImpl;

public class GameFrameImpl extends JFrame implements GameFrame {

    private final CardLayout layout;
    private final JPanel root;
    private final MenuPanel menuPanel;
    private final ShopView shopView;

    private static final String FRAME_NAME = "Road Hop";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public GameFrameImpl() {
        this.layout = new CardLayout();
        this.root = new JPanel(this.layout);
        this.menuPanel = new MenuPanelImpl();
        this.shopView = new ShopViewImpl();

        this.setTitle(FRAME_NAME);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        root.add((Component) menuPanel, CardName.MENU.toString());
        root.add((Component) shopView, CardName.SHOP.toString());
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void show(final CardName name) {
        this.layout.show(this.root, name.toString());
    }

    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }

    public ShopView getShopPanel() {
        return this.shopView;
    }

}
