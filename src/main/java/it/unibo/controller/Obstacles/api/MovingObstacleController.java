package it.unibo.controller.Obstacles.api;

import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;

public interface MovingObstacleController {

    /**
     * Avvia la generazione automatica degli ostacoli.
     * Gli ostacoli vengono generati a intervalli regolari basati sul livello di difficoltà.
     */
    void startObstacleGeneration();

    /**
     * Ferma la generazione automatica degli ostacoli.
     * Termina il servizio di generazione e pulisce le risorse.
     */
    void stopObstacleGeneration();

    /**
     * Crea un singolo ostacolo del tipo specificato.
     * 
     * @param type il tipo di ostacolo da creare
     * @param x la posizione X dell'ostacolo
     * @param y la posizione Y dell'ostacolo
     * @param speed la velocità dell'ostacolo
     * @return l'ostacolo creato
     */
    MovingObstacles createObstacle(ObstacleType type, int x, int y, int speed);

    /**
     * Crea un set di macchine sulla stessa riga.
     * 
     * @param y la posizione Y (riga) dove creare le macchine
     * @param count il numero di macchine da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli macchina creati
     */
    MovingObstacles[] createCarSet(int y, int count, boolean leftToRight);

    /**
     * Crea un set di treni sulla stessa riga.
     * 
     * @param y la posizione Y (riga) dove creare i treni
     * @param count il numero di treni da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli treno creati
     */
    MovingObstacles[] createTrainSet(int y, int count, boolean leftToRight);

    /**
     * Crea un set di tronchi sulla stessa riga.
     * I tronchi fungono da piattaforme sui fiumi e permettono al giocatore di attraversare l'acqua.
     * 
     * @param y la posizione Y (riga) dove creare i tronchi
     * @param count il numero di tronchi da creare
     * @param leftToRight la direzione di movimento (true = sinistra-destra, false = destra-sinistra)
     * @return array degli ostacoli tronco creati
     */
    MovingObstacles[] createLogSet(int y, int count, boolean leftToRight);

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

    /**
     * Aumenta il livello di difficoltà del gioco.
     * Incrementa la velocità degli ostacoli esistenti e la frequenza di generazione.
     * 
     * @param factor il fattore di incremento della difficoltà
     */
    void increaseDifficulty(int factor);

    /**
     * Resetta tutti gli ostacoli e riavvia la generazione.
     * Rimuove tutti gli ostacoli esistenti e reimposta il livello di difficoltà.
     */
    void resetObstacles();

    /**
     * Ottiene il livello di difficoltà corrente.
     * 
     * @return il livello di difficoltà attuale
     */
    int getCurrentDifficultyLevel();

    /**
     * Libera tutte le risorse utilizzate dal controller.
     * Deve essere chiamato quando il controller non è più necessario.
     */
    void dispose();

}
