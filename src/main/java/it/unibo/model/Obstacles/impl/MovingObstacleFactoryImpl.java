package it.unibo.model.Obstacles.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.api.MovingObstacleFactory;

public class MovingObstacleFactoryImpl implements MovingObstacleFactory {
    
    private final Random random;
    
    // Costanti per i limiti di velocità
    public static final int MIN_CAR_SPEED = 1;
    public static final int MAX_CAR_SPEED = 2;
    public static final int MIN_TRAIN_SPEED = 1;
    public static final int MAX_TRAIN_SPEED = 3;

    // Costanti per il sistema a griglia
    public static final int CELLS_PER_CHUNK = 9;
    
    public MovingObstacleFactoryImpl() {
        this.random = new Random();
    }
    
    @Override
    public MovingObstacles createCar(int x, int y, int speed) {
        return new MovingObstacles(x, y, ObstacleType.CAR, speed);
    }
    
    @Override
    public MovingObstacles createTrain(int x, int y, int speed) {
        return new MovingObstacles(x, y, ObstacleType.TRAIN, speed);
    }
    
    @Override
    public MovingObstacles createRandomCar(int y, int mapWidth, boolean leftToRight) {
        int speed = MIN_CAR_SPEED + random.nextInt(MAX_CAR_SPEED - MIN_CAR_SPEED + 1);
        if (!leftToRight) {
            speed = -speed;
        }
        
        // Posiziona l'auto fuori dal chunk visibile
        int startX = leftToRight ? -2 : CELLS_PER_CHUNK + 1;
        
        return createCar(startX, y, speed);
    }
    
    @Override
    public MovingObstacles createRandomTrain(int y, int mapWidth, boolean leftToRight) {
        int speed = MIN_TRAIN_SPEED + random.nextInt(MAX_TRAIN_SPEED - MIN_TRAIN_SPEED + 1);
        if (!leftToRight) {
            speed = -speed;
        }
        
        // I treni sono lunghi 4 celle, quindi devono partire più lontano
        // Assicurati che il treno sia completamente fuori schermo !
        int startX = leftToRight ? -MovingObstacles.TRAIN_WIDTH_CELLS - 1 : CELLS_PER_CHUNK + 1;
        
        return createTrain(startX, y, speed);
    }
    
    @Override
    public MovingObstacles[] createCarSet(int y, int mapWidth, int count, int minDistance, boolean leftToRight) {
        List<MovingObstacles> cars = new ArrayList<>();
        
        // Calcola le posizioni per distribuire le auto uniformemente
        int totalRange = CELLS_PER_CHUNK + 10; // Range esteso per includere aree fuori schermo
        int spacing = Math.max(minDistance, totalRange / count);
        
        for (int i = 0; i < count; i++) {
            int baseX = i * spacing;
            if (!leftToRight) {
                baseX = totalRange - baseX;
            }
            
            // Aggiungi un po' di randomizzazione
            int randomOffset = random.nextInt(spacing / 3) - spacing / 6;
            int finalX = baseX + randomOffset;
            
            // Assicurati che le auto non si sovrappongano
            boolean validPosition = true;
            for (MovingObstacles existingCar : cars) {
                if (Math.abs(existingCar.getX() - finalX) < 2) { // Minima distanza di 2 celle
                    validPosition = false;
                    break;
                }
            }
            
            if (validPosition) {
                int speed = MIN_CAR_SPEED + random.nextInt(MAX_CAR_SPEED - MIN_CAR_SPEED + 1);
                if (!leftToRight) {
                    speed = -speed;
                }
                
                cars.add(createCar(finalX, y, speed));
            }
        }
        
        return cars.toArray(MovingObstacles[]::new);
    }
    
    @Override
    public MovingObstacles[] createTrainSet(int y, int mapWidth, int count, int minDistance, boolean leftToRight) {
        List<MovingObstacles> trains = new ArrayList<>();
        
        // I treni richiedono più spazio (4 celle + distanza minima)
        int trainSpacing = Math.max(minDistance, 6); // Minimo 6 celle tra treni
        int totalRange = CELLS_PER_CHUNK + 20; // Range più ampio per i treni
        
        for (int i = 0; i < count; i++) {
            int baseX = i * trainSpacing;
            if (!leftToRight) {
                baseX = totalRange - baseX;
            }
            
            // Meno randomizzazione per i treni per evitare sovrapposizioni
            int randomOffset = random.nextInt(2) - 1; // -1, 0, o 1
            int finalX = baseX + randomOffset;
            
            // Controlla che non si sovrapponga con altri treni
            boolean validPosition = true;
            for (MovingObstacles existingTrain : trains) {
                int distance = Math.abs(existingTrain.getX() - finalX);
                if (distance < MovingObstacles.TRAIN_WIDTH_CELLS + minDistance) { // 4 celle del treno + 2 di sicurezza
                    validPosition = false;
                    break;
                }
            }
            
            if (validPosition) {
                int speed = MIN_TRAIN_SPEED + random.nextInt(MAX_TRAIN_SPEED - MIN_TRAIN_SPEED + 1);
                if (!leftToRight) {
                    speed = -speed;
                }
                
                trains.add(createTrain(finalX, y, speed));
            }
        }
        
        return trains.toArray(MovingObstacles[]::new);
    }

    /**
     * Crea un set di ostacoli per un chunk strada, alternando le direzioni.
     * 
     * @param chunkY Posizione Y del chunk
     * @param carCount Numero di auto da creare
     * @return Array di auto con direzioni alternate
     */
    @Override
    public MovingObstacles[] createRoadChunkObstacles(int chunkY, int carCount) {
        boolean leftToRight = random.nextBoolean();
        return createCarSet(chunkY, CELLS_PER_CHUNK, carCount, 3, leftToRight);
    }

    /**
     * Crea un set di ostacoli per un chunk ferrovia.
     * 
     * @param chunkY Posizione Y del chunk
     * @param trainCount Numero di treni da creare
     * @return Array di treni
     */
    @Override
    public MovingObstacles[] createRailwayChunkObstacles(int chunkY, int trainCount) {
        boolean leftToRight = random.nextBoolean();
        return createTrainSet(chunkY, CELLS_PER_CHUNK, trainCount, 8, leftToRight);
    }

     /**
     * Crea ostacoli appropriati per un tipo di chunk specifico.
     * 
     * @param chunkY Posizione Y del chunk
     * @param chunkType Tipo di chunk
     * @return Array di ostacoli mobili per il chunk
     */
    @Override
    public MovingObstacles[] createObstaclesForChunk(int chunkY, String chunkType) {
        return switch (chunkType.toUpperCase()) {
            case "ROAD" -> createRoadChunkObstacles(chunkY, 2 + random.nextInt(3)); // 2-4 auto
            case "RAILWAY" -> createRailwayChunkObstacles(chunkY, 1 + random.nextInt(2)); // 1-2 treni
            default -> new MovingObstacles[0]; // Nessun ostacolo mobile per altri tipi
        };
    }
}