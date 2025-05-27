package it.unibo.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import it.unibo.view.Menu.api.MenuPanel;
import it.unibo.view.Menu.impl.MenuPanelImpl;
import it.unibo.view.Shop.api.ShopView;
import it.unibo.view.Shop.impl.ShopViewImpl;

public class GameFrameImpl extends JFrame implements GameFrame {

    private final CardLayout layout;
    private final JPanel root;
    private final MenuPanel menuPanel;
    private final ShopView shopView;

    public GameFrameImpl() {
        this.layout = new CardLayout();
        this.root = new JPanel(this.layout);
        this.menuPanel = new MenuPanelImpl();
        this.shopView = new ShopViewImpl();

        this.setTitle("Road Hop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        root.add((Component) menuPanel, "menu");
        root.add((Component) shopView, "shop");
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void show(final String name) {
        this.layout.show(this.root, name);
    }

    public MenuPanel getMenuPanel() {
        return this.menuPanel;
    }

    public ShopView getShopPanel() {
        return this.shopView;
    }

}
