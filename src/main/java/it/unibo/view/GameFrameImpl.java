package it.unibo.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class GameFrameImpl extends JFrame implements GameFrame {

    public GameFrameImpl() {
        super("Road Hop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
    }

    @Override
    public void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();

        this.setSize(sw / 3, sh / 3);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

}
