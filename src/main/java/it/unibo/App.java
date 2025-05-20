package it.unibo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;   
import it.unibo.controller.Menu.api.MenuController;
import it.unibo.controller.Menu.impl.MenuControllerImpl;
import it.unibo.view.Menu.api.MenuView;

public class App {

    //costants for window size
    private static final int INITIAL_WIDTH = 800;
    private static final int INITIAL_HEIGHT = 600;
    private static final String WINDOW_TITLE = "Road Hop";

    public static void main(String[] args) {
         // Use SwingUtilities to ensure UI updates happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
         });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Create the main game panel
        MainGamePanel gamePanel = new MainGamePanel(INITIAL_WIDTH, INITIAL_HEIGHT);

        //initialize the menu controller and connects it to the view
        MenuView menuView = gamePanel.getMenuView();
        MenuController menuController = new MenuControllerImpl(menuView);
        gamePanel.setMenuController(menuController);

        // Add the game panel to the frame
        frame.getContentPane().add(gamePanel);

        //configure window size
        frame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        frame.setLocationRelativeTo(null); // Center the window

        //add window listener to resize the game panel
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt){
                gamePanel.resize(frame.getWidth(), frame.getHeight());
            }
        });

        frame.setVisible(true);

        startGameLoop(gamePanel);

    }

    private static void startGameLoop(MainGamePanel gamePanel) {
      Thread gameThread = new Thread(() -> {
            final int FPS = 60;
            final long OPTIMAL_TIME = 1000000000 / FPS;
            
            long lastUpdateTime = System.nanoTime();

            //Main game loop
            while (true) {
                long currentTime = System.nanoTime();
                long updateDelta = currentTime - lastUpdateTime;

                if (updateDelta >= OPTIMAL_TIME) {
                    gamePanel.update();
                    lastUpdateTime = currentTime;
                }

                //small delay to avoid CPU overload
                try{
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
      }) ;
        gameThread.setDaemon(true);
        gameThread.start();
    }

}