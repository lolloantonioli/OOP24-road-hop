package it.unibo.controller.Obstacles.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.unibo.model.Obstacles.api.MovingObstacleFactory;
import it.unibo.model.Obstacles.api.MovingObstacleManager;
import it.unibo.model.Obstacles.impl.MovingObstacleFactoryImpl;
import it.unibo.model.Obstacles.impl.MovingObstacleManagerImpl;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.controller.Obstacles.api.MovingObstacleController;
import it.unibo.model.Map.api.GameMap;
import it.unibo.model.Map.util.ObstacleType;

public class MovingObstacleControllerImpl implements MovingObstacleController{

    private final GameMap gameMap;
    private final MovingObstacleFactory obstacleFactory;
    private final MovingObstacleManager obstacleManager;
    private final Random random;
    
    // Parametri di configurazione
    private static final int MIN_DISTANCE_CARS = 100;
    private static final int MIN_DISTANCE_TRAINS = 300;
    private static final int CAR_SPAWN_INTERVAL_MS = 5000; // 5 secondi
    private static final int TRAIN_SPAWN_INTERVAL_MS = 15000; // 15 secondi
    
     // Generazione automatica di ostacoli
    private ScheduledExecutorService obstacleSpawner;
    
    // Difficoltà
    private int currentDifficultyLevel = 1;
    private int obstacleSpawnRate = 1;
    
    /**
     * Costruttore per MovingObstacleController.
     * 
     * @param gameMap La mappa di gioco
     */
    public MovingObstacleControllerImpl(GameMap gameMap) {
        this.gameMap = gameMap;
        this.obstacleFactory = new MovingObstacleFactoryImpl();
        this.random = new Random();
        
        // Recupera il manager esistente dalla mappa per evitare duplicazioni
        if (gameMap instanceof it.unibo.model.Map.impl.GameMapImpl gameMapImpl) {
            this.obstacleManager = gameMapImpl.getObstacleManager();
        } else {
            // Crea un nuovo manager se necessario
            this.obstacleManager = new MovingObstacleManagerImpl();
        }
    }
    
    /**
     * Inizializza e avvia la generazione automatica di ostacoli.
     * Da chiamare quando il gioco inizia.
     */
    @Override
    public void startObstacleGeneration() {
        if (obstacleSpawner != null && !obstacleSpawner.isShutdown()) {
            obstacleSpawner.shutdown();
        }
        
        obstacleSpawner = Executors.newScheduledThreadPool(2);
        
        // Pianifica la generazione periodica di auto
        obstacleSpawner.scheduleAtFixedRate(
            this::spawnRandomCars,
            2000, // Ritardo iniziale
            CAR_SPAWN_INTERVAL_MS / obstacleSpawnRate,
            TimeUnit.MILLISECONDS
        );
        
        // Pianifica la generazione periodica di treni
        obstacleSpawner.scheduleAtFixedRate(
            this::spawnRandomTrains,
            5000, // Ritardo iniziale
            TRAIN_SPAWN_INTERVAL_MS / obstacleSpawnRate, 
            TimeUnit.MILLISECONDS
        );
    }

   /**
     * Ferma la generazione automatica di ostacoli.
     * Da chiamare quando il gioco viene messo in pausa o terminato.
     */
    @Override
    public void stopObstacleGeneration() {
        if (obstacleSpawner != null && !obstacleSpawner.isShutdown()) {
            obstacleSpawner.shutdown();
            try {
                obstacleSpawner.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Genera auto casuali sulla mappa.
     * Chiamato automaticamente dall'obstacleSpawner.
     */
    private void spawnRandomCars() {
        // Trova le posizioni Y delle strade nella mappa visibile
        List<Integer> roadPositions = findRoadPositions();
        
        if (!roadPositions.isEmpty()) {
            // Scegli una strada casuale
            int roadY = roadPositions.get(random.nextInt(roadPositions.size()));
            
            // Il numero di auto dipende dal livello di difficoltà
            int carCount = Math.min(1 + currentDifficultyLevel / 2, 3);
            boolean leftToRight = random.nextBoolean();
            
            // Crea le auto
            createCarSet(roadY, carCount, leftToRight);
        }
    }
    
    /**
     * Genera treni casuali sulla mappa.
     * Chiamato automaticamente dall'obstacleSpawner.
     */
    private void spawnRandomTrains() {
        // Trova le posizioni Y delle ferrovie nella mappa visibile
        List<Integer> railwayPositions = findRailwayPositions();
        
        if (!railwayPositions.isEmpty()) {
            // Scegli una ferrovia casuale
            int railwayY = railwayPositions.get(random.nextInt(railwayPositions.size()));
            
            // I treni sono più pericolosi, quindi ne generiamo meno
            int trainCount = Math.min(1 + currentDifficultyLevel / 3, 2);
            boolean leftToRight = random.nextBoolean();
            
            // Crea i treni
            createTrainSet(railwayY, trainCount, leftToRight);
        }
    }
    
    /**
     * Trova le posizioni Y delle strade nella mappa visibile.
     * 
     * @return Lista di coordinate Y delle strade
     */
    private List<Integer> findRoadPositions() {
        // Implementazione semplificata - in un'implementazione reale, 
        // bisognerebbe analizzare i chunk della mappa per trovare le strade
        
        // Assumiamo alcune posizioni predefinite per esempio
        return List.of(
            gameMap.getCurrentPosition() + 200,
            gameMap.getCurrentPosition() + 400,
            gameMap.getCurrentPosition() + 600
        );
    }
    
    /**
     * Trova le posizioni Y delle ferrovie nella mappa visibile.
     * 
     * @return Lista di coordinate Y delle ferrovie
     */
    private List<Integer> findRailwayPositions() {
        // Implementazione semplificata
        
        // Assumiamo alcune posizioni predefinite per esempio
        return List.of(
            gameMap.getCurrentPosition() + 300,
            gameMap.getCurrentPosition() + 500
        );
    }
    
    /**
     * Crea e aggiunge un nuovo ostacolo mobile alla mappa.
     * 
     * @param type Tipo di ostacolo (CAR, TRAIN)
     * @param x Posizione X
     * @param y Posizione Y
     * @param speed Velocità (positiva = destra, negativa = sinistra)
     * @return L'ostacolo creato
     */
    @Override
    public MovingObstacles createObstacle(ObstacleType type, int x, int y, int speed) {
        MovingObstacles obstacle = null;
        
        switch (type) {
            case CAR -> obstacle = obstacleFactory.createCar(x, y, speed);
            case TRAIN -> obstacle = obstacleFactory.createTrain(x, y, speed);
            default -> throw new IllegalArgumentException("Tipo di ostacolo non supportato: " + type);
        }
        
        if (obstacle != null) {
            obstacleManager.addObstacle(obstacle);
        }
        
        return obstacle;
    }
    
    /**
     * Crea un gruppo di auto sulla strada.
     * 
     * @param y Posizione Y della strada
     * @param count Numero di auto da creare
     * @param leftToRight Direzione di movimento
     * @return Array di ostacoli creati
     */
    @Override
    public MovingObstacles[] createCarSet(int y, int count, boolean leftToRight) {
        MovingObstacles[] cars = obstacleFactory.createCarSet(
            y, 
            gameMap.getViewportWidth(),
            count,
            MIN_DISTANCE_CARS,
            leftToRight
        );
        
        obstacleManager.addObstacles(cars);
        return cars;
    }
    @Override
    public MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createTrainSet'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void increaseDifficulty(int factor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'increaseDifficulty'");
    }

    @Override
    public boolean checkCollision(int x, int y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkCollision'");
    }

    @Override
    public List<MovingObstacles> getObstaclesByType(ObstacleType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObstaclesByType'");
    }

    @Override
    public List<MovingObstacles> getAllObstacles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetObstacles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pauseObstacleGeneration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resumeObstacleGeneration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MovingObstacles createRandomObstacle(ObstacleType type, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCurrentDifficultyLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
