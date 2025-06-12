package it.unibo.controller.Obstacles.api;

import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public interface MovingObstacleController {

    /**
     * Crea un set di macchine sulla stessa riga.
     * 
     * @param y la posizione Y (riga) dove creare le macchine
     * @param count il numero di macchine da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli macchina creati
     */
    List<MovingObstacles> createCarSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Crea un set di treni sulla stessa riga.
     * 
     * @param y la posizione Y (riga) dove creare i treni
     * @param count il numero di treni da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli treno creati
     */
    List<MovingObstacles> createTrainSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Crea un set di tronchi sulla stessa riga.
     * I tronchi fungono da piattaforme sui fiumi e permettono al giocatore di attraversare l'acqua.
     * 
     * @param y la posizione Y (riga) dove creare i tronchi
     * @param count il numero di tronchi da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli tronco creati
     */
    List<MovingObstacles> createLogSet(int y, int count, boolean leftToRight, int speed);

    /**
     * Aggiorna tutti gli ostacoli mobili.
     * Deve essere chiamato ad ogni frame del gioco per aggiornare posizioni e stato.
     */
    void update();

    /**
     * Ottiene tutti gli ostacoli di un tipo specifico.
     * 
     * @param type il tipo di ostacolo richiesto
     * @return lista degli ostacoli del tipo specificato
     */
    List<MovingObstacles> getObstaclesByType(ObstacleType type);

    /**
     * Ottiene tutti gli ostacoli attualmente attivi.
     * 
     * @return lista di tutti gli ostacoli attivi
     */
    List<MovingObstacles> getAllObstacles();

    void resetObstacles();

    /**
     * Genera ostacoli in base al livello di difficoltà.
     * 
     * @param difficultyLevel il livello di difficoltà (1-3)
     * 1: Facile - pochi ostacoli
     * 2: Medio - ostacoli moderati
     * 3: Difficile - molti ostacoli
     */
    void generateObstacles(int difficultyLevel);

    /**
     * Aumenta i limiti di velocità per tutti gli ostacoli.
     * 
     * @param amount l'ammontare da aggiungere ai limiti di velocità
     */
    void increaseAllObstaclesSpeed(int i);

}
