package it.unibo.model.Obstacles.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

public class MovingObstacles implements Obstacle{
    private int x;
    private final int y;
    private final ObstacleType type;
    private int speed;
    private boolean movable;
    private boolean visible;
    private int initialX; // per far ricomparire l'ostacolo nella posizione iniziale
    private final int initialSpeed;
    private int mapWidth;

    public MovingObstacles(int x, int y, ObstacleType type, int speed) {
        this.x = x;
        this.initialX = x;
        this.y = y;
        this.type = type;
        this.speed = speed;
        this.initialSpeed = speed;
        this.movable = true;
        this.visible = true;
        this.mapWidth = 0;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public ObstacleType getType() {
        return type;
    }

    @Override
    public void update() {
         if (movable) {
            x += speed;
            
            // Gestione uscita dai bordi della mappa
            if (speed > 0 && x > mapWidth) {
                // Se l'ostacolo esce dal lato destro, lo riportiamo a sinistra
                x = -getWidth();
            } else if (speed < 0 && x + getWidth() < 0) {
                // Se l'ostacolo esce dal lato sinistro, lo riportiamo a destra
                x = mapWidth;
            }
        }
    }

    /**
     * Imposta una nuova larghezza per la mappa.
     * Utile quando le dimensioni della mappa cambiano dinamicamente.
     * 
     * @param mapWidth Nuova larghezza della mappa
     */
    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    /**
     * Reimposta gli ostacoli alla posizione iniziale.
     */
    public void reset() {
        this.x = initialX;
        this.speed = initialSpeed;
    }

    /**
     * Setta una nuova posizione iniziale.
     * 
     * @param newX Nuova X posizione iniziale
     */
    public void setInitialX(int newX) {
        this.initialX = newX;
        this.x = newX;
    }

    @Override
    public boolean collidesWith(int px, int py) {
        if (!visible) {
            return false;
        }
        return px >= x && px < x + getWidth() && py >= y && py < y + getHeight();
    }

    /**
     * Controlla se un ostacolo collide con un altro.
     * 
     * @param other Altro ostacolo con cui controllare la collisione
     * @return Vero se l'ostacolo collide
     */
    public boolean collidesWith(Obstacle other) {
        if (!visible) {
            return false;
        }
        
        // Verifica se i rettangoli si sovrappongono
        return x < other.getX() && 
               x + getWidth() > other.getX() &&
               y < other.getY() &&
               y + getHeight() > other.getY();
    }
    
    public int getWidth() {
        return type == ObstacleType.TRAIN ? 200 : 50;
    }
    
    public int getHeight() {
        return 40;
    }

    @Override
    public boolean isMovable() {
        return movable;
    }

    @Override
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Aumenta la velocità dell'ostacolo di una certa quantità.
     * 
     * @param amount Quantità di cui verrà aumentata
     */
    public void increaseSpeed(int amount) {
        // Mantiene il segno originale (direzione)
        if (this.speed > 0) {
            this.speed += amount;
        } else {
            this.speed -= amount;
        }
    }

    @Override
    public boolean isPlatform() {
        return false;
    }

    @Override
    public void setPlatform(boolean platform) {
        // non applicabile per CAR e TRAIN
        if (platform) {
            throw new UnsupportedOperationException("Gli ostacoli di tipo " + this.type + " non possono essere impostati come piattaforme");
        }
    }

     /**
     * Controlla se l'ostacolo è visibile.
     * 
     * @return Vero se è visibile.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Setta la visibilità dell'ostacolo.
     * 
     * @param visible Vero per segnare l'ostacolo visibile
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Ottiene il livello di difficoltà dell'ostacolo in base al tipo e alla velocità.
     * Utilizzato per il punteggio o per regolare la difficoltà.
     * 
     * @return Valore difficoltà
     */
    public int getDifficultyLevel() {
        int baseLevel = type == ObstacleType.TRAIN ? 3 : 1;
        return baseLevel + Math.abs(speed) / 2;
    }

}

