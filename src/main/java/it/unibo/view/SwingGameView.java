package it.unibo.view;

import javax.swing.JFrame;

public class SwingGameView extends JFrame implements GameView {

    public SwingGameView() {
        super("Road Hop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    @Override
    public void setupView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setupView'");
    }

}
