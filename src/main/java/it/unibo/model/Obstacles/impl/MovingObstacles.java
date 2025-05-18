package it.unibo.model.Obstacles.impl;

import it.unibo.model.Map.api.Obstacle;
import it.unibo.model.Map.util.ObstacleType;

//METODO UPDATE LASICARNE SOLO UNO! ++ MODIFICA MANAGER + FACTORY????

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
        this.mapWidth = mapWidth;
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
     * Resets the obstacle to its initial position.
     */
    public void reset() {
        this.x = initialX;
        this.speed = initialSpeed;
    }

    /**
     * Sets a new initial position for the obstacle.
     * 
     * @param newX New initial X position
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
     * Checks if this obstacle collides with another obstacle.
     * 
     * @param other Another obstacle to check collision with
     * @return True if the obstacles collide
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
     * Increases the speed of the obstacle by the specified amount.
     * 
     * @param amount Amount to increase speed by
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
    }

     /**
     * Checks if the obstacle is currently visible.
     * 
     * @return True if visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the obstacle.
     * 
     * @param visible True to make the obstacle visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets the obstacle's difficulty level based on its type and speed.
     * Used for scoring or difficulty adjustments.
     * 
     * @return Difficulty value
     */
    public int getDifficultyLevel() {
        int baseLevel = type == ObstacleType.TRAIN ? 3 : 1;
        return baseLevel + Math.abs(speed) / 2;
    }

}

