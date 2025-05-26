package it.unibo.view;

import javax.swing.JFrame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GameFrameImpl extends JFrame implements GameFrame {

    private final MenuPanel menuPanel;

    public GameFrameImpl() {
        super("Road Hop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPanel = new MenuPanelImpl();
    }

    @Override
    public void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();

        this.setContentPane((Container) menuPanel);
        this.setSize(sw / 3, sh / 3);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

}
