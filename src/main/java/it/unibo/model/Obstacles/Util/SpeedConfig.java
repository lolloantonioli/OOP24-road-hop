package it.unibo.model.Obstacles.Util;

import java.util.Random;

public class SpeedConfig {
    // valori iniziali
    public static int minCarSpeed = 15;
    public static int maxCarSpeed = 20;
    public static int minTrainSpeed = 25;
    public static int maxTrainSpeed = 30;
    public static int minLogSpeed = 10;
    public static int maxLogSpeed = 15;

    // helper per velocità casuale
    public static int randomCarSpeed(Random rnd) {
        return minCarSpeed + rnd.nextInt(maxCarSpeed - minCarSpeed + 1);
    }
    public static int randomTrainSpeed(Random rnd) {
        return minTrainSpeed + rnd.nextInt(maxTrainSpeed - minTrainSpeed + 1);
    }
    public static int randomLogSpeed(Random rnd) {
        return minLogSpeed + rnd.nextInt(maxLogSpeed - minLogSpeed + 1);
    }

    // (opzionale) metodo per variare tutti i limiti in blocco
    public static void increaseAllSpeeds(int delta) {
        minCarSpeed += delta;  
        maxCarSpeed += delta;
        minTrainSpeed += delta;  
        maxTrainSpeed += delta;
        minLogSpeed += delta;  
        maxLogSpeed += delta;
    }
}

