package it.unibo;

import it.unibo.view.GameFrame;
import it.unibo.view.GameFrameImpl;

public class App {
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrameImpl();
        gameFrame.display();
    }
}
