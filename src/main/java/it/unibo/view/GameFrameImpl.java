package it.unibo.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class GameFrameImpl extends JFrame implements GameFrame {

    public GameFrameImpl() {
        super("Road Hop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout(5, 5));
        this.setVisible(true);
    }

    @Override
    public void setupView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setupView'");
    }

}
