package it.unibo.view.Obstacles.api;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import it.unibo.model.Map.util.ObstacleType;
import it.unibo.model.Obstacles.impl.MovingObstacles;
import it.unibo.view.ScaleManager;

public interface MovingObstacleView {

    /**
     * Renderizza tutti gli ostacoli in movimento.
     * 
     * @param g Contesto grafico
     * @param obstacles Lista degli ostacoli in movimento da renderizzare
     * @param viewportOffset Lo spostamento Y del viewport
     */
    void renderObstacles(Graphics g, List<MovingObstacles> obstacles, int viewportOffset);

    /**
     * Aggiorna il gestore della scala quando cambiano le dimensioni dello schermo.
     * 
     * @param newScaleManager Il gestore della scala aggiornato
     */
    void updateScaleManager(ScaleManager newScaleManager);

    /**
     * Ottiene il rettangolo di collisione per un ostacolo in coordinate dello schermo.
     * Utile per il debugging e la visualizzazione delle collisioni.
     * 
     * @param obstacle L'ostacolo
     * @param viewportOffset Lo spostamento Y del viewport
     * @return Rettangolo che rappresenta i limiti dell'ostacolo sullo schermo
     */
    Rectangle getObstacleScreenBounds(MovingObstacles obstacle, int viewportOffset);

    /**
     * Personalizza il colore per uno specifico tipo di ostacolo.
     * 
     * @param type Il tipo di ostacolo
     * @param color Il nuovo colore
     */
    void setObstacleColor(ObstacleType type, Color color);
}
