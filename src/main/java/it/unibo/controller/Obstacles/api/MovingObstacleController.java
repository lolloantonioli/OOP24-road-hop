package it.unibo.controller.Obstacles.api;

import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

/**
 * Interfaccia per il controller dedicato alla gestione degli ostacoli mobili.
 * Gestisce la creazione, posizionamento, comportamento e spawn automatico di tutti gli ostacoli mobili.
 */
public interface MovingObstacleController {
    
    /**
     * Inizializza e avvia la generazione automatica di ostacoli.
     * Da chiamare quando il gioco inizia.
     */
    void startObstacleGeneration();
    
    /**
     * Ferma la generazione automatica di ostacoli.
     * Da chiamare quando il gioco viene messo in pausa o terminato.
     */
    void stopObstacleGeneration();
    
    /**
     * Crea e aggiunge un nuovo ostacolo mobile alla mappa.
     * 
     * @param type Tipo di ostacolo (CAR, TRAIN)
     * @param x Posizione X
     * @param y Posizione Y
     * @param speed Velocità (positiva = destra, negativa = sinistra)
     * @return L'ostacolo creato
     */
    MovingObstacles createObstacle(ObstacleType type, int x, int y, int speed);
    
    /**
     * Crea un gruppo di auto sulla strada.
     * 
     * @param y Posizione Y della strada
     * @param count Numero di auto da creare
     * @param leftToRight Direzione di movimento
     * @return Array di ostacoli creati
     */
    MovingObstacles[] createCarSet(int y, int count, boolean leftToRight);
    
    /**
     * Crea un gruppo di treni sulla ferrovia.
     * 
     * @param y Posizione Y della ferrovia
     * @param count Numero di treni da creare
     * @param leftToRight Direzione di movimento
     * @return Array di ostacoli creati
     */
    MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight);
    
    /**
     * Aggiorna tutti gli ostacoli mobili.
     * Da chiamare ad ogni ciclo di gioco.
     */
    void update();
    
    /**
     * Verifica se c'è una collisione con un ostacolo mobile.
     * 
     * @param x Coordinata X
     * @param y Coordinata Y
     * @return true se c'è una collisione
     */
    boolean checkCollision(int x, int y);
    
    /**
     * Ottiene tutti gli ostacoli attivi di un determinato tipo.
     * 
     * @param type Tipo di ostacolo
     * @return Lista di ostacoli del tipo specificato
     */
    List<MovingObstacles> getObstaclesByType(ObstacleType type);
    
    /**
     * Reimposta tutti gli ostacoli e riavvia la generazione.
     * Utile quando il gioco viene riavviato.
     */
    void resetObstacles();
    
    /**
     * Sospende temporaneamente la generazione di ostacoli.
     * Utile quando il gioco viene messo in pausa.
     */
    void pauseObstacleGeneration();
    
    /**
     * Riprende la generazione di ostacoli.
     * Utile quando il gioco viene ripreso dopo una pausa.
     */
    void resumeObstacleGeneration();
    
    /**
     * Crea un ostacolo casuale di un tipo specifico in una posizione casuale.
     * 
     * @param type Tipo di ostacolo
     * @param y Posizione Y approssimativa
     * @return L'ostacolo creato
     */
    MovingObstacles createRandomObstacle(ObstacleType type, int y);
    
    /**
     * Ottiene il livello di difficoltà corrente.
     * 
     * @return Il livello di difficoltà
     */
    int getCurrentDifficultyLevel();
    
    /**
     * Rilascia le risorse utilizzate dal controller.
     * Da chiamare quando il gioco viene terminato.
     */
    void dispose();
}
