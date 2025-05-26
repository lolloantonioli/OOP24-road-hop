package it.unibo.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class SwingView implements GameView {

    private final JFrame frame;
    private final MenuPanel menuPanel;

    public SwingView() {
        this.frame = new JFrame("Road Hop");
        this.menuPanel = new MenuPanelImpl();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize(new Dimension(800, 600));
        this.frame.add((Component) menuPanel);
    }

    public void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();

        this.frame.setSize(sw / 3, sh / 3);
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }

}
